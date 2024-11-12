package org.example.apiflutter.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//// Builder giúp tạo đối tượng với 1 số data nhất định chứ không cần tất cả data
@Builder
public class PermissionRequest {
    String name;
    String description;
}
