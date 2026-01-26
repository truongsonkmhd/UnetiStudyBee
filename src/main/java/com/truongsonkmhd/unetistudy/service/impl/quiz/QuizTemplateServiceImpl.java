package com.truongsonkmhd.unetistudy.service.impl.quiz;

import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseTemplateCardResponse;
import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizTemplateDTO;

import com.truongsonkmhd.unetistudy.exception.custom_exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.quiz.QuizTemplateMapper;
import com.truongsonkmhd.unetistudy.model.quiz.*;
import com.truongsonkmhd.unetistudy.model.quiz.template.AnswerTemplate;
import com.truongsonkmhd.unetistudy.model.quiz.template.QuestionTemplate;
import com.truongsonkmhd.unetistudy.model.quiz.template.QuizTemplate;
import com.truongsonkmhd.unetistudy.repository.quiz.QuizTemplateRepository;
import com.truongsonkmhd.unetistudy.service.QuizTemplateService;
import com.truongsonkmhd.unetistudy.validator.QuizTemplateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truongsonkmhd.unetistudy.utils.PageResponseBuilder;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizTemplateServiceImpl implements QuizTemplateService {

    private final QuizTemplateRepository templateRepository;
    private final QuizTemplateMapper templateMapper;
    private final QuizTemplateValidator templateValidator;

    @Override
    @Transactional
    public QuizTemplateDTO.DetailResponse createTemplate(QuizTemplateDTO.CreateRequest request, String createdBy) {
        log.info("Creating quiz template: {}", request.getTemplateName());

        QuizTemplate template = templateMapper.toEntity(request, createdBy);
        QuizTemplate savedTemplate = templateRepository.save(template);

        log.info("Quiz template created successfully with ID: {}", savedTemplate.getId());
        return templateMapper.toDetailResponse(savedTemplate);
    }

    @Override
    @Transactional
    public QuizTemplateDTO.DetailResponse updateTemplate(UUID templateId, QuizTemplateDTO.UpdateRequest request) {
        log.info("Updating quiz template: {}", templateId);

        QuizTemplate template = findTemplateOrThrow(templateId);
        templateValidator.validateVersion(template, request.getVersion());
        if (request.getTemplateName() != null) {
            template.setTitle(request.getTemplateName());
        }
        if (request.getDescription() != null) {
            template.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            template.setCategory(request.getCategory());
        }
        if (request.getThumbnailUrl() != null) {
            template.setThumbnailUrl(request.getThumbnailUrl());
        }
        if (request.getPassScore() != null) {
            template.setPassScore(request.getPassScore());
        }
        if (request.getTimeLimitMinutes() != null) {
            template.setTimeLimitMinutes(request.getTimeLimitMinutes());
        }
        if (request.getIsActive() != null) {
            template.setIsActive(request.getIsActive());
        }

        QuizTemplate updatedTemplate = templateRepository.save(template);
        log.info("Quiz template updated successfully: {}", templateId);

        return templateMapper.toDetailResponse(updatedTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizTemplateDTO.DetailResponse getTemplateById(UUID templateId) {
        log.debug("Fetching quiz template: {}", templateId);

        QuizTemplate template = templateRepository.findByIdWithQuestionsAndAnswers(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz template not found with ID: " + templateId));

        return templateMapper.toDetailResponse(template);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<QuizTemplateDTO.Response> searchTemplates(
            int page, int size, String category, Boolean isActive, String searchTerm) {

        log.info("=== SEARCH TEMPLATES START ===");
        log.info("Input params - page: {}, size: {}, category: '{}', isActive: {}, searchTerm: '{}'",
                page, size, category, isActive, searchTerm);

        // Validate and sanitize inputs
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100); // Max 100 items per page

        // Normalize empty strings to null for consistent query behavior
        String normalizedCategory = (category != null && category.trim().isEmpty()) ? null : category;
        String normalizedSearchTerm = (searchTerm != null && searchTerm.trim().isEmpty()) ? null : searchTerm;

        log.info("Normalized params - category: '{}', searchTerm: '{}'", normalizedCategory, normalizedSearchTerm);

        // Create pageable with sorting by createdAt descending
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        try {
            // Execute query
            Page<QuizTemplate> templatePage = templateRepository.searchTemplates(
                    normalizedCategory,
                    isActive,
                    normalizedSearchTerm,
                    pageable);

            log.info("Query executed - Found {} templates, Total pages: {}",
                    templatePage.getTotalElements(), templatePage.getTotalPages());

            // Map to DTO
            Page<QuizTemplateDTO.Response> responsePage = templatePage.map(template -> {
                try {
                    return templateMapper.toResponse(template);
                } catch (Exception e) {
                    log.error("Error mapping template ID: {} to response", template.getId(), e);
                    throw new RuntimeException("Error mapping template: " + template.getId(), e);
                }
            });

            // Build page response
            PageResponse<QuizTemplateDTO.Response> result = PageResponseBuilder.build(responsePage);

            log.info("=== SEARCH TEMPLATES SUCCESS ===");
            log.info("Returning {} items out of {} total", result.getItems().size(), result.getTotalElements());

            return result;

        } catch (Exception e) {
            log.error("=== SEARCH TEMPLATES ERROR ===", e);
            log.error("Error details - Type: {}, Message: {}", e.getClass().getName(), e.getMessage());
            throw new RuntimeException("Failed to search templates", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizTemplateDTO.Response> getMostUsedTemplates() {
        log.debug("Fetching most used templates");
        return templateRepository.findTop10ByIsActiveTrueOrderByUsageCountDesc()
                .stream()
                .map(templateMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        log.debug("Fetching all categories");
        return templateRepository.findAllCategories();
    }

    @Override
    @Transactional
    public Quiz createQuizFromTemplate(UUID templateId) {
        log.info("Creating quiz from template: {}", templateId);

        QuizTemplate template = templateRepository.findByIdWithQuestionsAndAnswers(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz template not found with ID: " + templateId));

        templateValidator.validateForQuizCreation(template);

        // Increment usage count
        template.incrementUsageCount();
        templateRepository.save(template);

        // Convert template to quiz
        Quiz quiz = templateMapper.templateToQuiz(template);

        log.info("Quiz created from template successfully. Template usage count: {}", template.getUsageCount());
        return quiz;
    }

    @Override
    @Transactional
    public boolean toggleTemplateStatus(UUID templateId, boolean isActive) {
        log.info("Toggling template status: {} to {}", templateId, isActive);

        QuizTemplate template = findTemplateOrThrow(templateId);
        template.setIsActive(isActive);
        templateRepository.save(template);

        log.info("Template status toggled successfully");
        return true;
    }

    @Override
    @Transactional
    public boolean deleteTemplate(UUID templateId) {
        log.info("Soft deleting quiz template: {}", templateId);

        QuizTemplate template = findTemplateOrThrow(templateId);
        template.softDelete();
        templateRepository.save(template);

        log.info("Quiz template soft deleted successfully");

        return true;
    }

    @Override
    @Transactional
    public QuizTemplateDTO.DetailResponse duplicateTemplate(UUID templateId, String newName) {
        log.info("Duplicating template: {} with new name: {}", templateId, newName);

        QuizTemplate original = templateRepository.findByIdWithQuestionsAndAnswers(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz template not found with ID: " + templateId));

        QuizTemplate duplicate = QuizTemplate.builder()
                .title(newName != null && !newName.trim().isEmpty() ? newName : original.getTitle() + " (Copy)")
                .description(original.getDescription())
                .category(original.getCategory())
                .thumbnailUrl(original.getThumbnailUrl())
                .passScore(original.getPassScore())
                .timeLimitMinutes(original.getTimeLimitMinutes())
                .createdBy(original.getCreatedBy())
                .isActive(true)
                .isDeleted(false)
                .build();

        // Duplicate questions and answers
        original.getQuestionTemplates().forEach(qt -> {
            QuestionTemplate duplicateQuestion = QuestionTemplate.builder()
                    .content(qt.getContent())
                    .questionOrder(qt.getQuestionOrder())
                    .timeLimitSeconds(qt.getTimeLimitSeconds())
                    .points(qt.getPoints())
                    .build();

            duplicate.addQuestionTemplate(duplicateQuestion);

            qt.getAnswerTemplates().forEach(at -> {
                AnswerTemplate duplicateAnswer = AnswerTemplate.builder()
                        .content(at.getContent())
                        .isCorrect(at.getIsCorrect())
                        .answerOrder(at.getAnswerOrder())
                        .build();
                duplicateQuestion.addAnswerTemplate(duplicateAnswer);
            });
        });

        QuizTemplate savedDuplicate = templateRepository.save(duplicate);
        log.info("Template duplicated successfully with ID: {}", savedDuplicate.getId());

        return templateMapper.toDetailResponse(savedDuplicate);
    }

    private QuizTemplate findTemplateOrThrow(UUID templateId) {
        return templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz template not found with ID: " + templateId));
    }
}