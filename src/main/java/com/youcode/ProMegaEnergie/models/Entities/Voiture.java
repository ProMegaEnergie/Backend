package com.youcode.ProMegaEnergie.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voiture")
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Matrecule;

    @ManyToOne
    private Batterie batterie;

    @ManyToOne
    private Societe societe;
}
