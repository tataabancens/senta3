package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


public class ReservationForm {

    @Size(min = 6, max = 50)
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
    private String mail;

    //@Size(min = 3, max = 50)
    //@Pattern(regexp = "[a-zA-Z]+ \\s [a-zA-Z]+")
    private String name;
    //@Size(min = 8, max = 8)
    //@Pattern(regexp = "[0-9]-[0-9]")
    private String phone;

    //private Timestamp reservationDate;

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    //public Timestamp getReservationDate() {
   //     return reservationDate;
   // }
}
