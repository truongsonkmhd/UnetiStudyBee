package com.truongsonkmhd.unetistudy.service.impl.course;

import com.truongsonkmhd.unetistudy.cache.CacheConstants;
import com.truongsonkmhd.unetistudy.dto.course_dto.CourseCardResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.CursorResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.repository.course.CourseRepository;
import com.truongsonkmhd.unetistudy.service.CourseCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Service quản lý Catalog khóa học với tích hợp Caching
 * 
 * Cache Patterns áp dụng:
 * 1. Cache-Aside - Cache danh sách courses đã publish
 * 2. Time-based Expiration - TTL 15 phút cho catalog
 * 3. LRU Eviction - Tự động loại bỏ các pages ít truy cập
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseCatalogServiceImpl implements CourseCatalogService {

    private final CourseRepository courseRepository;

    /**
     * Cache-Aside: Lấy danh sách courses đã publish (offset pagination)
     * Cache key: page + size + query
     * TTL: 15 phút
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConstants.COURSE_CATALOG, key = "'page:' + #page + ':size:' + #size + ':q:' + (#q ?: '')", unless = "#result.items.isEmpty()")
    public PageResponse<CourseCardResponse> getPublishedCourses(int page, int size, String q) {
        log.debug("Cache MISS - Loading published courses: page={}, size={}, q={}", page, size, q);

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(safePage, safeSize);
        Page<CourseCardResponse> result = (q == null || q.isBlank())
                ? courseRepository.findPublishedCourseCards(pageable)
                : courseRepository.searchPublishedCourseCards(q.trim(), pageable);

        return PageResponse.<CourseCardResponse>builder()
                .items(result.getContent())
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .hasNext(result.hasNext())
                .build();
    }

    /**
     * Cursor pagination - Không cache vì cursor unique cho mỗi request
     * Tuy nhiên các courses riêng lẻ được cache ở tầng khác
     */
    @Override
    @Transactional(readOnly = true)
    public CursorResponse<CourseCardResponse> getPublishedCoursesCursor(String cursor, int size, String q) {
        int safeSize = Math.min(Math.max(size, 1), 30);

        LocalDateTime publishedAt = null;
        UUID lastId = null;

        // Decode cursor
        if (cursor != null && !cursor.isBlank()) {
            try {
                String decoded = new String(Base64.getUrlDecoder().decode(cursor), StandardCharsets.UTF_8);
                String[] parts = decoded.split("\\|");
                if (parts.length == 2) {
                    long epoch = Long.parseLong(parts[0]);
                    publishedAt = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(epoch),
                            ZoneId.systemDefault());
                    lastId = UUID.fromString(parts[1]);
                }
            } catch (Exception e) {
                publishedAt = null;
                lastId = null;
            }
        }

        Pageable pageable = PageRequest.of(0, safeSize + 1);
        Page<CourseCardResponse> result;

        if (publishedAt == null || lastId == null) {
            result = (q == null || q.isBlank())
                    ? courseRepository.findPublishedCourseCards(pageable)
                    : courseRepository.searchPublishedCourseCards(q.trim(), pageable);
        } else {
            result = (q == null || q.isBlank())
                    ? courseRepository.findPublishedCourseCardsAfterCursor(publishedAt, lastId, pageable)
                    : courseRepository.searchPublishedCourseCardsAfterCursor(q.trim(), publishedAt, lastId, pageable);
        }

        List<CourseCardResponse> items = result.getContent();
        boolean hasNext = items.size() > safeSize;

        if (hasNext) {
            items = items.subList(0, safeSize);
        }

        String nextCursor = null;
        if (hasNext && !items.isEmpty()) {
            CourseCardResponse last = items.get(items.size() - 1);
            Instant instant = (last.getPublishedAt() != null)
                    ? last.getPublishedAt().atZone(ZoneId.systemDefault()).toInstant()
                    : Instant.now();

            long epoch = instant.toEpochMilli();
            String raw = epoch + "|" + last.getCourseId();
            nextCursor = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(raw.getBytes(StandardCharsets.UTF_8));
        }

        return CursorResponse.<CourseCardResponse>builder()
                .items(items)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}