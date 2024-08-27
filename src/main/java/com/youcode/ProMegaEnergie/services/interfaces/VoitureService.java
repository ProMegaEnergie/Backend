package com.youcode.ProMegaEnergie.services.interfaces;

import com.youcode.ProMegaEnergie.models.Dtos.VoitureDto.VoitureDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatVoiture;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;

import java.util.List;
import java.util.Optional;

public interface VoitureService {

    List<Voiture> getAllVoiture();

    List<Voiture> getAllVoiture(Long idSociete);

    Optional<Voiture> getVoiture(Long idVoiture);

    Boolean createVoiture(VoitureDto voitureDto);

    Boolean updateVoiture(VoitureDto voitureDto);

    List<Voiture> readAllVoitureByAchatStatus(AchatStatus achatStatus);

    List<Voiture> getVoituresByCherecher(String column, String cherecher);

    Boolean deleteVoiture(Long idVoiture);

    Boolean acheterVoiture(AchatVoiture achatVoiture);
}
