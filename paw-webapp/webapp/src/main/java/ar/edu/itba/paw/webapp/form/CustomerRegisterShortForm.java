package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.PasswordPair;
import ar.edu.itba.paw.webapp.form.customValidator.PasswordConstraint;
import ar.edu.itba.paw.webapp.form.customValidator.PasswordLengthConstraint;
import ar.edu.itba.paw.webapp.form.customValidator.UsernameConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRegisterShortForm {

    @UsernameConstraint
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    @Size(min = 1, max = 50)
    private String username;

    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String mail;

    @PasswordLengthConstraint
    @PasswordConstraint
    private PasswordPair psPair;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public PasswordPair getPsPair() {
        return psPair;
    }

    public void setPsPair(PasswordPair psPair) {
        this.psPair = psPair;
    }
}
