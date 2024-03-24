package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.models.Entities.Image;
import com.youcode.ProMegaEnergie.services.interfaces.UplodeImgService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("api/image")
public class ImageUploadController {

    private final UplodeImgService uplodeImgService;

    public ImageUploadController(UplodeImgService uplodeImgService) {
        this.uplodeImgService = uplodeImgService;
    }

    @PostMapping("/upload")
    public boolean uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        return this.uplodeImgService.upload(file);
    }
    @GetMapping(path = { "/get/{imageName}" })
    public Image getImage(@PathVariable("imageName") String imageName) throws IOException {
        return this.uplodeImgService.getImage(imageName);
    }
}
