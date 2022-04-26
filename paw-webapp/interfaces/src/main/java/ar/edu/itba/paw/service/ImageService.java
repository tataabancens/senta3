package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.RawImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    Optional<RawImage> getImageById(long id);

    Image createImage(MultipartFile photo);

}
