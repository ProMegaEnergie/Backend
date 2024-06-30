package com.youcode.CRMEF.models.Dtos.ChefFiliereDto;

import com.youcode.CRMEF.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChefFiliereDto {
    private Long id;
    private String email;
    private String image;
    private String Nom;
    private String password;
    private RoleUser roleUser;
    private Boolean isVerifie;
}
