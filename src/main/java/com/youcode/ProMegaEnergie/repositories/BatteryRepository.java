package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Batterie, Long> {
    Optional<Batterie> findByIdAndAgent_Id(Long id, Long idAgent);
    List<Batterie> findAllByAgent_Id(Long idAgent);
}
