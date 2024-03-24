package com.youcode.ProMegaEnergie.services.interfaces;

import com.youcode.ProMegaEnergie.models.Entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UplodeImgService {

    boolean upload(MultipartFile file) throws IOException;

    Image getImage(String imageName);
}
