package com.youcode.ProMegaEnergie.services.interfaces;

import com.youcode.ProMegaEnergie.models.Dtos.VoitureDto.VoitureDto;
import com.youcode.ProMegaEnergie.models.Entities.Voiture;

import java.util.List;
import java.util.Optional;

public interface VoitureService {

    List<Voiture> getAllVoiture();

    List<Voiture> getAllVoiture(Long idSociete);

    Optional<Voiture> getVoiture(Long idVoiture);

    Boolean createVoiture(VoitureDto voitureDto);

    Boolean updateVoiture(VoitureDto voitureDto);
}
