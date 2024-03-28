package com.youcode.ProMegaEnergie.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "achatVoiture")
public class AchatVoiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Voiture voiture;

    @ManyToOne
    private Client client;
}
