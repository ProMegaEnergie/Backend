package com.youcode.CRMEF.models.Entities;

import com.youcode.CRMEF.models.Enums.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ChefFiliere")
public class ChefFiliere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String image;
    private String Nom;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser;
    private Boolean isVerifie;
}