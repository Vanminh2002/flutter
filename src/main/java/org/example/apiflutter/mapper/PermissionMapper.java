package org.example.apiflutter.mapper;

import org.example.apiflutter.dto.request.PermissionRequest;
import org.example.apiflutter.dto.response.PermissionResponse;
import org.example.apiflutter.dto.response.UserResponse;
import org.example.apiflutter.entity.Permission;
import org.example.apiflutter.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
