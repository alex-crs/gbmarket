package com.gb.market.api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationUserDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
