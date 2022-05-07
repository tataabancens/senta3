package ar.edu.itba.paw.model;

//import org.springframework.web.multipart.MultipartFile;

public class RawImage {
    private long imageId;
    private byte[] bytes;

    public RawImage(long imageId, byte[] bytes) {
        this.imageId = imageId;
        this.bytes = bytes;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
