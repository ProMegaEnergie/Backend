package com.youcode.CRMEF.repositories;

import com.youcode.CRMEF.models.Entities.ChefFiliere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ChefFiliereRepository extends JpaRepository<ChefFiliere, Long> {

    Optional<ChefFiliere> findByEmailAndPassword(String email, String password);
    Boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<ChefFiliere> findByEmail(String email);
}
