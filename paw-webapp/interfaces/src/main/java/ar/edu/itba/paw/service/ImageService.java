package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    Optional<Image> getImageById(long id);

    Image createImage(MultipartFile photo);
}
