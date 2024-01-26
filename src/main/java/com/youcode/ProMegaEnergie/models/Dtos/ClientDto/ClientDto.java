package com.youcode.ProMegaEnergie.models.Dtos.ClientDto;

import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private Long id;
    private String email;
    private String password;
    private String Nom;
    private String Prenom;
    private RoleUser roleUser;
    private Boolean isVerifie;
}
