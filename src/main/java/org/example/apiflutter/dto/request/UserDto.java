package org.example.apiflutter.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//// Builder giúp tạo đối tượng với 1 số data nhất định chứ không cần tất cả data
//@Builder
public class UserDto {
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;
    @Size(min = 4, message = "PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
