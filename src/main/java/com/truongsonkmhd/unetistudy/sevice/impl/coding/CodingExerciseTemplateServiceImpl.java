package com.truongsonkmhd.unetistudy.sevice.impl.coding;

import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseTemplateDTO;
import com.truongsonkmhd.unetistudy.dto.exercise_test_cases_dto.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.model.lesson.template.CodingExerciseTemplate;
import com.truongsonkmhd.unetistudy.model.lesson.template.ExerciseTemplateTestCase;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseTemplateRepository;
import com.truongsonkmhd.unetistudy.sevice.CodingExerciseTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.truongsonkmhd.unetistudy.common.Difficulty;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseTemplateCardResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.CursorResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CodingExerciseTemplateServiceImpl implements CodingExerciseTemplateService {

    private final CodingExerciseTemplateRepository repository;

    @Override
    @Transactional
    public CodingExerciseTemplate save(CodingExerciseTemplateDTO dto) {
        CodingExerciseTemplate template = CodingExerciseTemplate.builder()
                .category(dto.getCategory())
                .tags(dto.getTags())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .programmingLanguage(dto.getProgrammingLanguage())
                .difficulty(dto.getDifficulty())
                .points(dto.getPoints())
                .isPublished(dto.getIsPublished())
                .timeLimitMs(dto.getTimeLimitMs())
                .memoryLimitMb(dto.getMemoryLimitMb())
                .initialCode(dto.getInitialCode())
                .solutionCode(dto.getSolutionCode())
                .slug(dto.getSlug())
                .inputFormat(dto.getInputFormat())
                .outputFormat(dto.getOutputFormat())
                .constraintName(dto.getConstraintName())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build() ;

        int orderIndex = 1;
        for (ExerciseTestCasesDTO tcDto : dto.getExerciseTestCases()) {

            ExerciseTemplateTestCase testCase = ExerciseTemplateTestCase.builder()
                    .template(template)
                    .input(tcDto.getInput())
                    .expectedOutput(tcDto.getExpectedOutput())
                    .isSample(Boolean.TRUE.equals(tcDto.getIsPublic()))
                    .orderIndex(orderIndex++)
                    .build();

            template.addTestCase(testCase);
        }

        return repository.save(template);
    }

    // ========== OFFSET PAGINATION ==========

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CodingExerciseTemplateCardResponse> getPublishedTemplates(
            int page, int size) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(safePage, safeSize);
        Page<CodingExerciseTemplateCardResponse> result = repository.findPublishedTemplates(pageable);

        return buildPageResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CodingExerciseTemplateCardResponse> getAllTemplates(
            int page, int size) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(safePage, safeSize);
        Page<CodingExerciseTemplateCardResponse> result = repository.findAllTemplates(pageable);

        return buildPageResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CodingExerciseTemplateCardResponse> searchTemplates(
            int page, int size, String q, Difficulty difficulty,
            String category, String language) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(safePage, safeSize);
        Page<CodingExerciseTemplateCardResponse> result = repository.searchTemplates(
                q, difficulty, category, language, pageable
        );

        return buildPageResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CodingExerciseTemplateCardResponse> searchAllTemplates(
            int page, int size, String q, Difficulty difficulty,
            String category, String language, Boolean published) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(safePage, safeSize);
        Page<CodingExerciseTemplateCardResponse> result = repository.searchAllTemplates(
                q, difficulty, category, language, published, pageable
        );

        return buildPageResponse(result);
    }

    // ========== CURSOR PAGINATION ==========

    @Override
    @Transactional(readOnly = true)
    public CursorResponse<CodingExerciseTemplateCardResponse> getPublishedTemplatesCursor(
            String cursor, int size) {

        int safeSize = Math.min(Math.max(size, 1), 30);

        Instant createdAt = null;
        UUID lastId = null;

        if (cursor != null && !cursor.isBlank()) {
            try {
                String decoded = new String(Base64.getUrlDecoder().decode(cursor), StandardCharsets.UTF_8);
                String[] parts = decoded.split("\\|");
                if (parts.length == 2) {
                    long epoch = Long.parseLong(parts[0]);
                    createdAt = Instant.ofEpochMilli(epoch);
                    lastId = UUID.fromString(parts[1]);
                }
            } catch (Exception e) {
                // Invalid cursor, start from beginning
            }
        }

        Pageable pageable = PageRequest.of(0, safeSize + 1);
        Page<CodingExerciseTemplateCardResponse> result;

        if (createdAt == null || lastId == null) {
            result = repository.findPublishedTemplates(pageable);
        } else {
            result = repository.findPublishedTemplatesAfterCursor(createdAt, lastId, pageable);
        }

        return buildCursorResponse(result.getContent(), safeSize);
    }

    @Override
    @Transactional(readOnly = true)
    public CursorResponse<CodingExerciseTemplateCardResponse> searchTemplatesCursor(
            String cursor, int size, String q, Difficulty difficulty,
            String category, String language) {

        int safeSize = Math.min(Math.max(size, 1), 30);

        Instant createdAt = null;
        UUID lastId = null;

        if (cursor != null && !cursor.isBlank()) {
            try {
                String decoded = new String(Base64.getUrlDecoder().decode(cursor), StandardCharsets.UTF_8);
                String[] parts = decoded.split("\\|");
                if (parts.length == 2) {
                    long epoch = Long.parseLong(parts[0]);
                    createdAt = Instant.ofEpochMilli(epoch);
                    lastId = UUID.fromString(parts[1]);
                }
            } catch (Exception e) {
                // Invalid cursor
            }
        }

        Pageable pageable = PageRequest.of(0, safeSize + 1);
        Page<CodingExerciseTemplateCardResponse> result;

        if (createdAt == null || lastId == null) {
            result = repository.searchTemplates(q, difficulty, category, language, pageable);
        } else {
            result = repository.searchTemplatesAfterCursor(
                    q, difficulty, category, language, createdAt, lastId, pageable
            );
        }

        return buildCursorResponse(result.getContent(), safeSize);
    }

    // ========== HELPER METHODS ==========

    private PageResponse<CodingExerciseTemplateCardResponse> buildPageResponse(
            Page<CodingExerciseTemplateCardResponse> page) {
        return PageResponse.<CodingExerciseTemplateCardResponse>builder()
                .items(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .build();
    }

    private CursorResponse<CodingExerciseTemplateCardResponse> buildCursorResponse(
            List<CodingExerciseTemplateCardResponse> items, int requestedSize) {

        boolean hasNext = items.size() > requestedSize;
        if (hasNext) {
            items = items.subList(0, requestedSize);
        }

        String nextCursor = null;
        if (hasNext && !items.isEmpty()) {
            CodingExerciseTemplateCardResponse last = items.get(items.size() - 1);
            long epoch = last.getCreatedAt().toEpochMilli();
            String raw = epoch + "|" + last.getTemplateId();
            nextCursor = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(raw.getBytes(StandardCharsets.UTF_8));
        }

        return CursorResponse.<CodingExerciseTemplateCardResponse>builder()
                .items(items)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}