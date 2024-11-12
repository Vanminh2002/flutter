package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.UserDto;
import org.example.apiflutter.dto.request.UserUpdateDto;
import org.example.apiflutter.dto.response.UserResponse;
import org.example.apiflutter.entity.User;
import org.example.apiflutter.enums.Role;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.ErrorCode;

import org.example.apiflutter.mapper.UserMapper;
import org.example.apiflutter.repository.RoleRepository;
import org.example.apiflutter.repository.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServices {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    //Create
//    @PreAuthorize("hasRole('ADMIN')")
    public User createRequest(UserDto userDto) {


        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
//            throw new RuntimeException("ErrorCode.USER_EXISTED");
        }
//        if(user != null){
//            throw  new RuntimeException("User Existed");
//        }
//        User user = new User();
        User user = userMapper.toUSer(userDto);
//
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);


        return userRepository.save(user);
    }

    // Get All

    //map với ROLE
//    @PreAuthorize("hasRole('ADMIN')")
    // thực hiện trước khi vào method
//Map với permission
//    @PreAuthorize("hasAuthority('Read')")
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    // GetById
    //  được thực hiện sau method nếu thỏa mãn điều kiện thì mới trả kết quả về
//    @PostAuthorize("hasRole('ADMIN')")
//    @PostAuthorize("returnObject.username == authentication.name")
    // cái này có thể dùng trong trường hợp tài khoản ai tạo thì mới sửa được
    public UserResponse getById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    // Update
//    @PreAuthorize("hasRole('ADMIN')")

    public UserResponse update(UserUpdateDto userDto, String id) {

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));


        userMapper.updateUser(user, userDto);
        // lấy list role theo id
        var roles = roleRepository.findAllById(userDto.getRoles());

        user.setRoles(new HashSet<>(roles));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }


    // Delete
//    @PreAuthorize("hasRole('ADMIN')")

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    //    @PostAuthorize("returnObject.username == authentication.name")
//     cái này có thể dùng trong trường hợp tài khoản ai tạo thì mới sửa được
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
