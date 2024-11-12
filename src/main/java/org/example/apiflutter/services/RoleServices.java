package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.apiflutter.dto.request.RoleRequest;
import org.example.apiflutter.dto.response.RoleResponse;
import org.example.apiflutter.entity.Role;
import org.example.apiflutter.mapper.RoleMapper;
import org.example.apiflutter.repository.PermissionRepository;
import org.example.apiflutter.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.stream.Collectors;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServices {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);

    }

    public List<RoleResponse> getAll() {



            return roleRepository.findAll().
                stream()
                .map(roleMapper::toRoleResponse).toList();
    }
    public void delete(String name) {
        roleRepository.deleteById(name);
    }

}
