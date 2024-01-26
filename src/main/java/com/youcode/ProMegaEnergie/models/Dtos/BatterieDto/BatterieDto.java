package com.youcode.ProMegaEnergie.models.Dtos.BatterieDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatterieDto {
    private Long id;
    private String Nom;
    private float Voltage;
    private String Vis;
    private float Prix;
}
