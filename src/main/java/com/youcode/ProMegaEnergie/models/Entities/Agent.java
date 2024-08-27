package com.youcode.ProMegaEnergie.models.Entities;

import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String Nom;
    private String Prenom;
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser;
    private Boolean isVerifie;
}