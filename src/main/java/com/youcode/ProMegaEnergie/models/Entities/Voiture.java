package com.youcode.ProMegaEnergie.models.Entities;

import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
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
    private String matrecule;
    private Float prix;

    @Enumerated(EnumType.STRING)
    private AchatStatus achatStatus;

    @ManyToOne
    private Batterie batterie;

    @ManyToOne
    private Societe societe;
}
