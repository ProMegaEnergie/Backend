package com.youcode.CRMEF.repositories;

import com.youcode.CRMEF.models.Entities.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FormateurRepository extends JpaRepository<Formateur, Long> {

    Optional<Formateur> findByEmailAndPassword(String email, String password);
    Boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<Formateur> findByEmail(String email);
}
