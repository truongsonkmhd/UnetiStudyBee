package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.course_dto.CourseCardResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.CursorResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;

public interface CourseCatalogService {
    PageResponse<CourseCardResponse> getPublishedCourses(int page, int size, String q);
    CursorResponse<CourseCardResponse> getPublishedCoursesCursor(String cursor, int size, String q);
}
