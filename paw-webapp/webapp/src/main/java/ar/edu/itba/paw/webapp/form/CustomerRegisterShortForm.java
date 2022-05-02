package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRegisterShortForm {

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private String username;

    private String Password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
