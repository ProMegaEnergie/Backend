package com.youcode.ProMegaEnergie.models.Dtos.VoitureDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoitureResponseDto {
    private Long id;
    private String matrecule;
    private String image;
    private Float prix;
}
