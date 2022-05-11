package ar.edu.itba.paw.webapp.form.CustomValidator;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.PasswordPair;
import ar.edu.itba.paw.webapp.form.CustomerRegisterShortForm;

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
