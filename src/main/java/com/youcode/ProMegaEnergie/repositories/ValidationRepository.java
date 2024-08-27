package com.youcode.ProMegaEnergie.repositories;

import com.youcode.ProMegaEnergie.models.Entities.Validation;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    boolean existsByEmailAndCodeAndRoleUser(String email, long code, RoleUser roleUser);
}
