package com.truongsonkmhd.unetistudy.sevice.impl.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseCardResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.CursorResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.repository.CourseRepository;
import com.truongsonkmhd.unetistudy.sevice.CourseCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;

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
    // 2) Cursor pagination (recommended for infinite scroll)
    // cursor chứa publishedAt + courseId để ổn định
    // -------------------------
    @Override
    @Transactional(readOnly = true)
    public CursorResponse<CourseCardResponse> getPublishedCoursesCursor(String cursor, int size, String q) {
        int safeSize = Math.min(Math.max(size, 1), 30);

        // decode cursor (publishedAtEpochMillis|courseId)
        Instant publishedAt = null;
        String lastId = null;

        if (cursor != null && !cursor.isBlank()) {
            String decoded = new String(Base64.getUrlDecoder().decode(cursor), StandardCharsets.UTF_8);
            String[] parts = decoded.split("\\|");
            if (parts.length == 2) {
                long epoch = Long.parseLong(parts[0]);
                publishedAt = Instant.ofEpochMilli(epoch);
                lastId = parts[1];
            }
        }

        PageResponse<CourseCardResponse> page = getPublishedCourses(0, safeSize, q);
        List<CourseCardResponse> items = page.getItems();

        String nextCursor = null;
        boolean hasNext = page.isHasNext();

        if (!items.isEmpty() && hasNext) {
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
