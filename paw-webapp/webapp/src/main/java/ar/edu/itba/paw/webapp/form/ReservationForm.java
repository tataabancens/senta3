package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.CustomValidator.DateConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class ReservationForm {

    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String mail;

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private String name;
    @Size(min = 9, max = 13)
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$")
    private String phone;

    private int hour;
    private int qPeople;

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getqPeople() {
        return qPeople;
    }

    public void setqPeople(int qPeople) {
        this.qPeople = qPeople;
    }
}
