package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;


public class ReservationForm {

    @Size(min = 6, max = 50)
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
    private String mail;

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-z ,.'-]+$")
    private String name;
    @Size(min = 10, max = 13)
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$")
    private String phone;
    private Date date;
    @Size(min = 4,max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])$")
    private String time;


    public String getTime() {
        return time;
    }
    public Long getTimeInEpoch(){
        String[] units=getTime().split(":");

        return Long.parseLong(units[0])*3600+Long.parseLong(units[1])*60;
    }

    public Date getDate() {
        return date;
    }


    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

}
