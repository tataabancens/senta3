package ar.edu.itba.paw.model.exceptions;

public class CategoryNameAlreadyExistsException extends RuntimeException {
    public CategoryNameAlreadyExistsException() {
        super("Category name already exists");
    }
}
