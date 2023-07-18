package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.persistance.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Transactional
    @Override
    public Optional<Image> getImageById(long id) {
        return imageDao.getImageById(id);
    }

    @Transactional
    @Override
    public long createImage(byte[] photo) {
        try {
            return imageDao.create(photo);
        } catch(Exception e) {
            return -1;
        }
    }
}
