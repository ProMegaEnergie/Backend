package com.youcode.ProMegaEnergie.models.Dtos.VoitureDto;

import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Entities.Societe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoitureDto {
    private Long id;
    private String matrecule;
    private Batterie batterie;
    private Societe societe;
    private Float prix;
    private String image;
}
