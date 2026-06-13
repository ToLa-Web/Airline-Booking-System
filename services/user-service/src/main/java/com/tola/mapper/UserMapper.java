package com.tola.mapper;

import com.tola.model.User;
import com.tola.payload.dto.UserDTO;
import java.util.List;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {return null;}

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {return null;}
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
