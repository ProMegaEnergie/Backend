package com.youcode.CRMEF.models.Entities;

import com.youcode.CRMEF.models.Enums.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long code;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser;
}
