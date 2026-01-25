package com.truongsonkmhd.unetistudy.exception.cutom_exeption;

import com.truongsonkmhd.unetistudy.exception.ErrorCode;

/**
 * Exception for forbidden access
 */
public class ForBiddenException extends BaseException {
    public ForBiddenException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }

    public ForBiddenException() {
        super(ErrorCode.FORBIDDEN, "You don't have permission to access this resource");
    }
}