package com.guraride.guraridebooking_system.mapper;


import com.guraride.guraridebooking_system.dto.UserDto;
import com.guraride.guraridebooking_system.models.User;

public class UserMapper {
    public static User mapToUser(UserDto user){
        User userDto = User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .nationalId(user.getNationalId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .status(user.getStatus())
                .password(user.getPassword())
                .createdOn(user.getCreatedOn())
                .updateOn(user.getUpdateOn())
                .build();
        return userDto;
    }
    public static UserDto mapToUserDto(User user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .nationalId(user.getNationalId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .status(user.getStatus())
                .password(user.getPassword())
                .createdOn(user.getCreatedOn())
                .updateOn(user.getUpdateOn())
                .build();
        return userDto;
    }
}
