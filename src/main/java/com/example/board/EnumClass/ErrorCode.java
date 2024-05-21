package com.example.board.EnumClass;

public enum ErrorCode {
    FILE_VALID_ERROR("File validation error"),
    FILE_FORMAT_ERROR("File format error"),
    FILE_UPLOAD_ERROR("File upload error");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
