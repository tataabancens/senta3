package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditRestaurantEmailForm {
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
