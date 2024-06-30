package com.youcode.CRMEF.repositories;

import com.youcode.CRMEF.models.Entities.Validation;
import com.youcode.CRMEF.models.Enums.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    boolean existsByEmailAndCodeAndRoleUser(String email, long code, RoleUser roleUser);
}
