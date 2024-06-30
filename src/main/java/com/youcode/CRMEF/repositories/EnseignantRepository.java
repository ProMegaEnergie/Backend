package com.youcode.CRMEF.repositories;

import com.youcode.CRMEF.models.Entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    Optional<Enseignant> findByEmailAndPassword(String email, String password);
    Boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<Enseignant> findByEmail(String email);
}
