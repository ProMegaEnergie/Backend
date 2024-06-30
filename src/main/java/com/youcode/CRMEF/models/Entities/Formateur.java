package com.youcode.CRMEF.models.Entities;

import com.youcode.CRMEF.models.Enums.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Formateur")
public class Formateur {
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