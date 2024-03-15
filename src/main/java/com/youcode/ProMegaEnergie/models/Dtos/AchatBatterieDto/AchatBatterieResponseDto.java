package com.youcode.ProMegaEnergie.models.Dtos.AchatBatterieDto;

import com.youcode.ProMegaEnergie.models.Enums.StatusBattery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchatBatterieResponseDto {
    private Long id;
    private StatusBattery statusBattery;
}
