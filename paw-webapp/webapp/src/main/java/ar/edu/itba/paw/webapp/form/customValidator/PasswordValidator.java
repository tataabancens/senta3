package ar.edu.itba.paw.webapp.form.customValidator;

import ar.edu.itba.paw.model.PasswordPair;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, PasswordPair> {

    @Override
    public void initialize(PasswordConstraint orderConstraint) {
    }

    @Override
    public boolean isValid(PasswordPair pair, ConstraintValidatorContext constraintValidatorContext) {
        if (pair == null || pair.getPassword() == null || pair.getCheckPassword() == null) {
            return true;
        }
        return pair.getPassword().compareTo(pair.getCheckPassword()) == 0;
    }
}
