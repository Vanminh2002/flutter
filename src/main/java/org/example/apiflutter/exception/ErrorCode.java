package org.example.apiflutter.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "UNCATEGORIZED_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1000, "User Existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1001, "User Not Found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1002, "Username must be at least 4 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be at least 4 characters", HttpStatus.BAD_REQUEST),
    KEY_INVALID(1004, "Invalid Key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005, "UNAUTHENTICATED", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    NOTFOUND(401, "Not Found", HttpStatus.NOT_FOUND),
    PRODUCT_NOTFOUND(300, "PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    HttpStatusCode statusCode;
    int code;
    String message;

}
