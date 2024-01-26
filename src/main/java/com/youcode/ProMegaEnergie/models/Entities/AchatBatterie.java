package com.youcode.ProMegaEnergie.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "achatBatterie")
public class AchatBatterie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Batterie batterie;

    @ManyToOne
    private Societe societe;
}
