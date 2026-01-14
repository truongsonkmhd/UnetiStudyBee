package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCardResponse {
    private UUID courseId;
    private String title;
    private String slug;
    private String shortDescription;
    private String imageUrl;

    private Boolean isPublished;
    private Integer totalModules;
    private LocalDateTime publishedAt;
}