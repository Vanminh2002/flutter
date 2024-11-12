package org.example.apiflutter.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.entity.Role;

import java.time.LocalDate;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<RoleResponse> roles;
}
