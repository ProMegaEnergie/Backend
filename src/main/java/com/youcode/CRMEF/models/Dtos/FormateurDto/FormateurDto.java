package com.youcode.CRMEF.models.Dtos.FormateurDto;

import com.youcode.CRMEF.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormateurDto {
    private Long id;
    private String email;
    private String password;
    private String Nom;
    private String Prenom;
    private RoleUser roleUser;
    private Boolean isVerifie;
}
