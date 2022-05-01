package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditREstaurantPhoneForm {

    @Size(min = 9, max = 13)
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
