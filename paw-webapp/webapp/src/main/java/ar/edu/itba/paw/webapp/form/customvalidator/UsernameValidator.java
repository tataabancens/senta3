package ar.edu.itba.paw.webapp.form.customvalidator;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    @Autowired
    private UserService us;

    @Override
    public void initialize(UsernameConstraint usernameConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;
        Optional<User> maybeUser = us.getUserByUsername(s);
        return !maybeUser.isPresent();
    }
}
