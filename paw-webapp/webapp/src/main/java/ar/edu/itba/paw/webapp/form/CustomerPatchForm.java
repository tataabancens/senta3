package ar.edu.itba.paw.webapp.form;

public class CustomerPatchForm {
    private String name;
    private String phone;
    private String mail;
    private Long userId;
//    private Integer points; //can't patch points


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

//    public Integer getPoints() {
//        return points;
//    }
//
//    public void setPoints(Integer points) {
//        this.points = points;
//    }
}
