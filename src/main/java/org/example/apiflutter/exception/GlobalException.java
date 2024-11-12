package org.example.apiflutter.exception;

import org.example.apiflutter.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlerRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlerMethodArgument(MethodArgumentNotValidException exception) {

        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

//       ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        ErrorCode errorCode = ErrorCode.KEY_INVALID;

        try {
            errorCode = ErrorCode.valueOf(enumKey);

        } catch (IllegalArgumentException e) {

        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);

    }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlerAppException(AppException exception) {

        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());

        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse
                );

    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlerAccessDeniedException(AccessDeniedException exception) {
            ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );

    }
    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse> handlerAuthorizationDeniedException(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );

    }


}
