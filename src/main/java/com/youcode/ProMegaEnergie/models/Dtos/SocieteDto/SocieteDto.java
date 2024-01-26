package com.youcode.ProMegaEnergie.models.Dtos.SocieteDto;

import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocieteDto {
    private Long id;
    private String email;
    private String image;
    private String Nom;
    private String password;
    private RoleUser roleUser;
    private Boolean isVerifie;
}
