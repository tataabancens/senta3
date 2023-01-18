package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.PasswordPair;
import ar.edu.itba.paw.webapp.form.customValidator.PasswordConstraint;
import ar.edu.itba.paw.webapp.form.customValidator.PasswordLengthConstraint;
import ar.edu.itba.paw.webapp.form.customValidator.UsernameConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserForm {

    @NotNull
    @UsernameConstraint
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String username;

    @NotNull
    @PasswordLengthConstraint
    @PasswordConstraint
    private PasswordPair psPair;

    @NotNull
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PasswordPair getPsPair() {
        return psPair;
    }

    public void setPsPair(PasswordPair psPair) {
        this.psPair = psPair;
    }
}
