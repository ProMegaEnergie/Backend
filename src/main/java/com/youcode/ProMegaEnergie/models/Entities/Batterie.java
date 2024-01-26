package com.youcode.ProMegaEnergie.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "batterie")
public class Batterie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Nom;
    private float Voltage;
    private String Vis;
    private float Prix;
}
