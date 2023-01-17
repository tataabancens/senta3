package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.customValidator.UsernameConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserForm {

    @UsernameConstraint
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String username;


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
}
