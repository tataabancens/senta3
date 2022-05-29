package ar.edu.itba.paw.model;

//import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_imageid_seq")
    @SequenceGenerator(sequenceName = "image_imageid_seq", name = "image_imageid_seq", allocationSize = 1)
    @Column(name = "imageid")
    private long id;

    @Column(name = "bitmap")
    private byte[] bytes;

    /* default */ Image() {
        // Just for hibernate
    }

    public Image(byte[] bytes) {
        this.bytes = bytes;
    }

    public long getImageId() {
        return id;
    }

    public void setImageId(long imageId) {
        this.id = imageId;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
