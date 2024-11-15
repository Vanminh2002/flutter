package org.example.apiflutter.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.ImageRequest;
import org.example.apiflutter.dto.request.ProductRequest;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
// Builder giúp tạo đối tượng với 1 số data nhất định chứ không cần tất cả data
@Builder
public class ApiResponse<T> {
    int code = 300;
    String message;
    T result;

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(200, "Success", result);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(400, message, null);
    }


//    public ApiResponse(String s, ProductRequest productDto) {
//    }
}
