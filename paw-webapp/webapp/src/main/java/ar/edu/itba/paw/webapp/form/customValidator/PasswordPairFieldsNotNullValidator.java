package ar.edu.itba.paw.webapp.form.customValidator;

import ar.edu.itba.paw.model.PasswordPair;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordPairFieldsNotNullValidator implements ConstraintValidator<PasswordPairFieldsNotNullConstraint, PasswordPair> {
    @Override
    public void initialize(PasswordPairFieldsNotNullConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordPair value, ConstraintValidatorContext context) {
        return value.getPassword() != null && value.getCheckPassword() != null;
    }
}
