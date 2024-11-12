package org.example.apiflutter.mapper;

import org.example.apiflutter.dto.request.RoleRequest;
import org.example.apiflutter.dto.response.RoleResponse;
import org.example.apiflutter.entity.Role;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
