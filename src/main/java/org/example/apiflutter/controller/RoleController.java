package org.example.apiflutter.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.PermissionRequest;
import org.example.apiflutter.dto.request.RoleRequest;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.PermissionResponse;
import org.example.apiflutter.dto.response.RoleResponse;
import org.example.apiflutter.entity.Permission;
import org.example.apiflutter.entity.Role;
import org.example.apiflutter.services.PermissionServices;
import org.example.apiflutter.services.RoleServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleServices roleServices;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return  ApiResponse.<RoleResponse>builder()
                .result(roleServices.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleServices.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable String name) {
        roleServices.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}
