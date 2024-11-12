package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.apiflutter.dto.request.PermissionRequest;
import org.example.apiflutter.dto.response.PermissionResponse;
import org.example.apiflutter.entity.Permission;
import org.example.apiflutter.mapper.PermissionMapper;
import org.example.apiflutter.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServices {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
