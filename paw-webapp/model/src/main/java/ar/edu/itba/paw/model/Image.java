package ar.edu.itba.paw.model;

import org.springframework.web.multipart.MultipartFile;

public class Image {
    private long imageId;
    private MultipartFile photo;

    public Image(long imageId, MultipartFile photo) {
        this.imageId = imageId;
        this.photo = photo;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
