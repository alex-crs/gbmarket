package com.gb.market.auth.converters;

import com.gb.market.api.dtos.UserDTO;
import com.gb.market.auth.entities.Role;
import com.gb.market.auth.entities.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserConverter {
    public static UserDTO userToUserDTOConverter(User user) {
        List<String> listRoles = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), listRoles);
    }
}
