package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageDao {
    Optional<Image> getImageById(long id);

    long create(byte[] photo) throws IOException;

    void deleteImageById(long imageId);
}
