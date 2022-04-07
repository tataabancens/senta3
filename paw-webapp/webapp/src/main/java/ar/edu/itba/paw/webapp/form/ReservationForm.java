package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class ReservationForm {

    @Size(min = 6, max = 50)
    @Pattern(regexp = "[a-z0-9]+@[a-z]+[a-z]{2,3}")
    private String mail;

    @Size(min = 3, max = 50)
    @Pattern(regexp = "[a-zA-Z]+ \\s [a-zA-Z]+")
    private String name;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

}
