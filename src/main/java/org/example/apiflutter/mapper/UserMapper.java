package org.example.apiflutter.mapper;

import org.example.apiflutter.dto.request.UserDto;
import org.example.apiflutter.dto.request.UserUpdateDto;
import org.example.apiflutter.dto.response.UserResponse;
import org.example.apiflutter.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUSer(UserDto userDto);

    UserDto toUserDto(User user);

    // map khác filed , source là nguồn , targer là đối tượng map về
//    @Mapping(source = "firstName", target = "lastName")
    // không map trương lastName
//    @Mapping( target = "lastName",ignore = true)

    UserResponse toUserResponse(User user);
    @Mapping(target = "roles",ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateDto userUpdateDto);


}
