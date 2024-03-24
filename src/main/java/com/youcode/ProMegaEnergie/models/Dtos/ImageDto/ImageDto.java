package com.youcode.ProMegaEnergie.models.Dtos.ImageDto;

import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {
    private Long id;
    private String name;
    private String type;
    private byte[] picByte;
}
