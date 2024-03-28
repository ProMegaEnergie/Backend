package com.youcode.ProMegaEnergie.models.Dtos.UserDto;

import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private String password;
    private RoleUser roleUser;

    public UserDto() {
    }

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
