package com.youcode.CRMEF.models.Dtos.AdminDto;

import com.youcode.CRMEF.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDto {
    private Long id;
    private String email;
    private String password;
    private RoleUser roleUser;
}
