package com.youcode.ProMegaEnergie.services.interfaces;

import com.youcode.ProMegaEnergie.models.Dtos.AchatBatterieDto.AchatBatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieResponseDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatBatterie;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;

import java.util.List;
import java.util.Optional;

public interface BatterieService {
    List<Batterie> getBatteries();

    Boolean acheterBatterie(AchatBatterieDto achatBatterieDto);

    List<AchatBatterie> getBatteriesAchetes(Long idSociete);

    BatterieResponseDto CreateBatterie(BatterieDto batterieDto);

    List<Batterie> ReadBatterie(Long idAgent);

    Optional<Batterie> ReadBatterieById(Long id, Long idAgent);

    BatterieResponseDto UpdateBatterie(BatterieDto batterieDto);

    Boolean DeleteBatterie(Long id);
}
