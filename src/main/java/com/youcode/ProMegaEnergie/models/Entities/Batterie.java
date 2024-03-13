package com.youcode.ProMegaEnergie.models.Entities;

import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
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
    private String Vis;
    private float Prix;
    @Enumerated(EnumType.STRING)
    private AchatStatus achatStatus;
    @ManyToOne
    private Agent agent;
}
