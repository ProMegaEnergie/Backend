package com.youcode.ProMegaEnergie.models.Dtos.BatterieDto;

import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatterieResponseDto {
    private Long id;
    private String Nom;
    private String Vis;
    private float Prix;
    private AchatStatus achatStatus;
}
