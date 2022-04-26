package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.RawImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageDao {
    Optional<RawImage> getImageById(long id);

    Image create(MultipartFile photo) throws IOException;

    void deleteImageById(long imageId);
}
