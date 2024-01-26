package com.youcode.ProMegaEnergie.models.Dtos.AgentDto;

import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDto {
    private Long id;
    private String email;
    private String password;
    private String Nom;
    private String Prenom;
    private RoleUser roleUser;
    private Boolean isVerifie;
}
