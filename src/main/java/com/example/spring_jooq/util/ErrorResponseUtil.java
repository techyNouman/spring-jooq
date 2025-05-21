package com.example.spring_jooq.util;

import com.example.spring_jooq.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorResponseUtil {
    public static ErrorResponse buildError(int status, String error, String code, String message, HttpServletRequest request) {
        return new ErrorResponse(status, error, code, message, request.getRequestURI());
    }
}
