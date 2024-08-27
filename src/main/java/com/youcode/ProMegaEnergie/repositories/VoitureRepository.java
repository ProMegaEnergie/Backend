package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findAllBySociete_Id(Long idSociete);

    List<Voiture> findAllByAchatStatus(AchatStatus achatStatus);

    @Query("SELECT e FROM Voiture e WHERE LOWER(e.matrecule) LIKE LOWER(CONCAT('%', :cherecher, '%')) AND e.achatStatus = :achatStatus")
    List<Voiture> findAllByMatreculeContainingIgnoreCaseAndAchatStatus(@Param("cherecher") String cherecher, @Param("achatStatus") AchatStatus achatStatus);

    List<Voiture> findAllByPrixAndAchatStatus(float prix, AchatStatus achatStatus);

    @Query("SELECT e FROM Voiture e WHERE e.prix = :prix AND LOWER(e.matrecule) LIKE LOWER(CONCAT('%', :matrecule, '%')) AND e.achatStatus = :achatStatus")
    List<Voiture> findAllByPrixAndMatreculeContainingIgnoreCaseAndAchatStatus(float prix, String matrecule, AchatStatus achatStatus);
}
