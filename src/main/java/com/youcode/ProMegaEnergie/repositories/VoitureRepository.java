package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findAllBySociete_Id(Long idSociete);
}
