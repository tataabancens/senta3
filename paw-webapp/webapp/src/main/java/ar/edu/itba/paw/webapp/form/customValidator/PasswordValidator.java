package ar.edu.itba.paw.webapp.form.customValidator;

import ar.edu.itba.paw.model.PasswordPair;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, PasswordPair> {

    @Override
    public void initialize(PasswordConstraint orderConstraint) {

    }

    @Override
    public boolean isValid(PasswordPair pair, ConstraintValidatorContext constraintValidatorContext) {
        return pair.getPassword().compareTo(pair.getCheckPassword()) == 0;
    }
}
