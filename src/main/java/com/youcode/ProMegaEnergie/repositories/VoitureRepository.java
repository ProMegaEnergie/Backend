package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findAllBySociete_Id(Long idSociete);

    List<Voiture> findAllByAchatStatus(AchatStatus achatStatus);

    List<Voiture> findAllByMatreculeAndAchatStatus(String cherecher, AchatStatus achatStatus);

    List<Voiture> findAllByPrixAndAchatStatus(float prix, AchatStatus achatStatus);

    List<Voiture> findAllByPrixAndMatreculeAndAchatStatus(float prix, String matrecule, AchatStatus achatStatus);
}
