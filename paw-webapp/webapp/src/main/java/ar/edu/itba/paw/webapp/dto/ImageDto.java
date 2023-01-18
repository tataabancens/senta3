package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Image;

public class ImageDto {

    private long id;
    private byte[] bitmap;

    public ImageDto() {
        // for jersey
    }

    public static ImageDto fromImage(Image image){
        ImageDto imageDto =  new ImageDto();

        imageDto.id = image.getImageId();
        imageDto.bitmap = image.getBytes();

        return imageDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }
}
