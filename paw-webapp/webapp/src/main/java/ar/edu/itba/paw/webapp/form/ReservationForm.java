package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.CustomValidator.DateTimeConstraint;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class ReservationForm {

    @Size(min = 6, max = 50)
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
    private String mail;

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private String name;
    @Size(min = 9, max = 13)
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$")
    private String phone;
    @NotEmpty
    //@DateTimeConstraint
    private String timeAndDate;

    public String getTimeAndDate() {
        return timeAndDate;
    }
    public Timestamp getTimeStamp(){
        return Timestamp.valueOf(LocalDateTime.parse(timeAndDate));
    }
    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

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

}
