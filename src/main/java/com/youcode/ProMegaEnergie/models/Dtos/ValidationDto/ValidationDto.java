package com.youcode.ProMegaEnergie.models.Dtos.ValidationDto;

import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationDto {
    private Long id;
    private Long code;
    private String email;
    private RoleUser roleUser;
    private AchatStatus achatStatus;
}
