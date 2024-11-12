package org.example.apiflutter.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//// Builder giúp tạo đối tượng với 1 số data nhất định chứ không cần tất cả data
@Builder
public class RoleResponse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}