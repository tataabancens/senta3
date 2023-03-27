package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {
    Optional<Image> getImageById(long id);

    long createImage(byte[] photo);

}
