package ar.edu.itba.paw.webapp.form;

public class OrderItemPatchForm {
    private String status;
    private String securityCode;

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
