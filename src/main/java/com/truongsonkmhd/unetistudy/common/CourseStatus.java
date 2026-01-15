package com.truongsonkmhd.unetistudy.common;

public enum CourseStatus {
    DRAFT,              // tạo/sửa bình thường
    PENDING_APPROVAL,   // đã gửi yêu cầu duyệt
    APPROVED,           // admin duyệt
    REJECTED            // admin từ chối
}