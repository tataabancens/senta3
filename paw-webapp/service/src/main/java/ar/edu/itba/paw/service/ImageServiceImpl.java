package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.persistance.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Optional<Image> getImageById(long id) {
        return imageDao.getImageById(id);
    }

    @Override
    public Image createImage(MultipartFile photo) {
        try {
            return imageDao.create(photo);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
