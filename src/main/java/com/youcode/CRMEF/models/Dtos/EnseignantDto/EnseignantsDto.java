package com.youcode.CRMEF.models.Dtos.EnseignantDto;

import com.youcode.CRMEF.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnseignantsDto {
    private Long id;
    private String email;
    private String password;
    private String Nom;
    private String Prenom;
    private RoleUser roleUser;
    private Boolean isVerifie;
}
