package org.example.apiflutter.mapper;

import org.example.apiflutter.dto.response.ImageResponse;
import org.example.apiflutter.entity.Images;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ImageMapper {
    ImageResponse toImageResponse(Images image);
}
