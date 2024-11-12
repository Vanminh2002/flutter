package org.example.apiflutter.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyTokenResponse {
    boolean valid;
}
