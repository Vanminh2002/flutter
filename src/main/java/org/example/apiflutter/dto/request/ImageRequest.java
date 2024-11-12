package org.example.apiflutter.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageRequest {
    Long id;
    String fileName;
    String downloadUrl;
}
