package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.AchatBatterie;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchatBatterieRepository extends JpaRepository<AchatBatterie, Long> {
    List<AchatBatterie> findAllBySocieteId(Long idSociete);
}
