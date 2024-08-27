package com.youcode.ProMegaEnergie.models.Dtos.BatterieDto;

import com.youcode.ProMegaEnergie.models.Entities.Agent;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatterieDto {
    private Long id;
    private String nom;
    private String vis;
    private float prix;
    private Agent agent;
    private AchatStatus achatStatus;
    private String image;
}
