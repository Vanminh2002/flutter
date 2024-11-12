package org.example.apiflutter.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
// Builder giúp tạo đối tượng với 1 số data nhất định chứ không cần tất cả data
@Builder
public class UserUpdateDto {
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    List<String> roles;
}
