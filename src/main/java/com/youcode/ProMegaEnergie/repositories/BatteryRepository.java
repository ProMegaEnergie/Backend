package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Batterie, Long> {
    Optional<Batterie> findByIdAndAgent_Id(Long id, Long idAgent);
    List<Batterie> findAllByAgent_Id(Long idAgent);
    List<Batterie> findAllByAchatStatus(AchatStatus achatStatus);
    @Query("SELECT e FROM Batterie e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :cherecher, '%')) AND e.achatStatus = :achatStatus")
    List<Batterie> findByNomContainingIgnoreCaseAndAchatStatus(@Param("cherecher") String cherecher, @Param("achatStatus") AchatStatus achatStatus);
    List<Batterie> findByPrixAndAchatStatus(float prix, AchatStatus achatStatus);
    @Query("SELECT e FROM Batterie e WHERE e.prix = :prix AND LOWER(e.nom) LIKE LOWER(CONCAT('%', :nom, '%')) AND e.achatStatus = :achatStatus")
    List<Batterie> findByPrixAndNomContainingIgnoreCaseAndAchatStatus(float prix, String nom, AchatStatus achatStatus);
}
