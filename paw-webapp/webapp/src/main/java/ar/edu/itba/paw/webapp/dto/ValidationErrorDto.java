package ar.edu.itba.paw.webapp.dto;

import javax.validation.ConstraintViolation;

public class ValidationErrorDto {

    private String message;
    private String field;

    public static ValidationErrorDto fromValidationException(final ConstraintViolation<?> violation) {
        final ValidationErrorDto dto = new ValidationErrorDto();
        dto.message = violation.getMessage();
        String[] splitPath =  violation.getPropertyPath().toString().split("[.]");
        dto.field = splitPath[splitPath.length - 1];
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
