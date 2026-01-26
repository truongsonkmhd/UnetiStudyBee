package com.truongsonkmhd.unetistudy.service.impl.course;

import com.truongsonkmhd.unetistudy.dto.course_dto.CourseCardResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.CursorResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.repository.course.CourseRepository;
import com.truongsonkmhd.unetistudy.service.CourseCatalogService;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class CourseCatalogServiceImpl implements CourseCatalogService {

    private final CourseRepository courseRepository;

    // -------------------------
    // 1) Offset pagination (page/size) - đơn giản
    // -------------------------
    @Override
    @Transactional(readOnly = true)
    public PageResponse<CourseCardResponse> getPublishedCourses(int page, int size, String q) {
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

    // -------------------------
    // 2) Cursor pagination - PHẢI CẬP NHẬT CourseRepository
    // -------------------------
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
                // Invalid cursor, start from beginning
                publishedAt = null;
                lastId = null;
            }
        }

        // Fetch data with cursor - CẦN THÊM METHOD MỚI VÀO REPOSITORY
        Pageable pageable = PageRequest.of(0, safeSize + 1); // +1 để check hasNext
        Page<CourseCardResponse> result;

        if (publishedAt == null || lastId == null) {
            // First page
            result = (q == null || q.isBlank())
                    ? courseRepository.findPublishedCourseCards(pageable)
                    : courseRepository.searchPublishedCourseCards(q.trim(), pageable);
        } else {
            // Subsequent pages - CẦN THÊM METHOD MỚI
            result = (q == null || q.isBlank())
                    ? courseRepository.findPublishedCourseCardsAfterCursor(publishedAt, lastId, pageable)
                    : courseRepository.searchPublishedCourseCardsAfterCursor(q.trim(), publishedAt, lastId, pageable);
        }

        List<CourseCardResponse> items = result.getContent();
        boolean hasNext = items.size() > safeSize;

        if (hasNext) {
            items = items.subList(0, safeSize);
        }

        // Build next cursor
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