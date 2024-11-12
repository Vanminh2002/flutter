package org.example.apiflutter.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//// Builder giúp tạo đối tượng với 1 số data nhất định chứ không cần tất cả data
@Builder
public class RoleRequest {
    String name;
    String description;
    Set<String> permissions;

}
