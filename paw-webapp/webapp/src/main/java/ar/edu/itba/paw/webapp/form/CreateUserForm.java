package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.PasswordPair;
import ar.edu.itba.paw.webapp.form.customvalidator.PasswordConstraint;
import ar.edu.itba.paw.webapp.form.customvalidator.PasswordLengthConstraint;
import ar.edu.itba.paw.webapp.form.customvalidator.PasswordPairFieldsNotNullConstraint;
import ar.edu.itba.paw.webapp.form.customvalidator.UsernameConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserForm {

    @NotNull
    @UsernameConstraint
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String username;

    @PasswordPairFieldsNotNullConstraint
    @PasswordLengthConstraint
    @PasswordConstraint
    @NotNull
    private PasswordPair psPair;

    @NotNull
    private String role;

    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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
