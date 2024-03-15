package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Batterie, Long> {
    Optional<Batterie> findByIdAndAgent_Id(Long id, Long idAgent);
    List<Batterie> findAllByAgent_Id(Long idAgent);
    List<Batterie> findAllByAchatStatus(AchatStatus achatStatus);
    List<Batterie> findAllByPrixAndAchatStatus(float Prix, AchatStatus achatStatus);
    List<Batterie> findAllByPrixAndNomAndAchatStatus(float Prix, String nom, AchatStatus achatStatus);
    List<Batterie> findAllByNomAndAchatStatus(String cherecher, AchatStatus achatStatus);
}
