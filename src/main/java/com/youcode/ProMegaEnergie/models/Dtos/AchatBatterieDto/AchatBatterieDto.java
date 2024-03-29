package com.youcode.ProMegaEnergie.models.Dtos.AchatBatterieDto;

import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Entities.Societe;
import com.youcode.ProMegaEnergie.models.Enums.StatusBattery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchatBatterieDto {
    private Long id;
    private Batterie batterie;
    private Societe societe;
    private StatusBattery statusBattery;
}
