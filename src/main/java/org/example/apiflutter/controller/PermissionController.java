package org.example.apiflutter.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.PermissionRequest;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.PermissionResponse;
import org.example.apiflutter.entity.Permission;
import org.example.apiflutter.services.PermissionServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionServices permissionServices;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest permissionRequest) {
//        ApiResponse<PermissionReponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(permissionServices.create(permissionRequest));
//        return apiResponse;
        Permission permission = Permission.builder()
                .name(permissionRequest.getName())
                .description(permissionRequest.getDescription())
                .build();
        return ApiResponse.<PermissionResponse>builder()

                .result(permissionServices.create(permissionRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionServices.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable String name) {
        permissionServices.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}
