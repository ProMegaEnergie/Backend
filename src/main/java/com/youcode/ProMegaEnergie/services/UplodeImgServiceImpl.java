package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Entities.Image;
import com.youcode.ProMegaEnergie.repositories.ImageRepositories;
import com.youcode.ProMegaEnergie.services.interfaces.UplodeImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class UplodeImgServiceImpl implements UplodeImgService {
    @Autowired
    ImageRepositories imageRepositories;
    @Override
    public boolean upload(MultipartFile file) throws IOException {
        Image img = new Image(file.getOriginalFilename(), file.getContentType(),compressBytes(file.getBytes()));
        imageRepositories.save(img);
        return true;
    }

    @Override
    public Image getImage(String imageName) {
        final Optional<Image> retrievedImage = imageRepositories.findAllByName(imageName);
        return new Image(retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
