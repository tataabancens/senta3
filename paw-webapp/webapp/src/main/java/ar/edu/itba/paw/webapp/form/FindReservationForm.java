package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;

public class FindReservationForm {

    private String securityCode;

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
