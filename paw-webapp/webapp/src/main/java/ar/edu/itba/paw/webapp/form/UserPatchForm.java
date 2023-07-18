package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.PasswordPair;
import ar.edu.itba.paw.webapp.form.customvalidator.PasswordConstraint;
import ar.edu.itba.paw.webapp.form.customvalidator.PasswordLengthConstraint;
import ar.edu.itba.paw.webapp.form.customvalidator.UsernameConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserPatchForm {

    @UsernameConstraint
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String username;

    @PasswordLengthConstraint
    @PasswordConstraint
    private PasswordPair psPair;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PasswordPair getPsPair() {
        return psPair;
    }

    public void setPsPair(PasswordPair password) {
        this.psPair = password;
    }
}
