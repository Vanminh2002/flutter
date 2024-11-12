package org.example.apiflutter.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.UserDto;
import org.example.apiflutter.dto.request.UserUpdateDto;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.UserResponse;
import org.example.apiflutter.entity.User;
import org.example.apiflutter.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserServices userServices;

    @PostMapping
    ApiResponse<User> create(@Valid @RequestBody UserDto userDto) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userServices.createRequest(userDto));
        return apiResponse;
    }


    @GetMapping
    ApiResponse<List<UserResponse>> getAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username:{}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userServices.getAll())
                .build();
//        return userServices.getAll();
    }


    @GetMapping("/{id}")
    ApiResponse<UserResponse> getById(@PathVariable String id) {

        return ApiResponse.<UserResponse>builder()
                .result(userServices.getById(id))
                .build();

//        return userServices.getById(id);
    }

    @PutMapping("/{id}")
    UserResponse update(@PathVariable String id, @RequestBody UserUpdateDto userUpdateDto) {

        return userServices.update(userUpdateDto, id);
    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable String id) {
        userServices.delete(id);
        return "User has been deleted";
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userServices.getMyInfo())
                .build();
    }
}
