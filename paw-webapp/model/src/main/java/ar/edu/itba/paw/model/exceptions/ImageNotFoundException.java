package ar.edu.itba.paw.model.exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() {
        super("Image does not exist");
    }
}
