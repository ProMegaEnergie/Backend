package com.youcode.ProMegaEnergie.models.Dtos.AchatVoitureDto;

import com.youcode.ProMegaEnergie.models.Entities.Client;
import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchatVoitureDto {
    private Long id;
    private Voiture voiture;
    private Client client;
}
