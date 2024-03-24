package com.youcode.ProMegaEnergie.models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image_table")
public class Image {

    public Image(){
        super();
    }
    public Image(String name,String type, byte[] picByte){
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Column(length = 1000)
    private byte[] picByte;
}
