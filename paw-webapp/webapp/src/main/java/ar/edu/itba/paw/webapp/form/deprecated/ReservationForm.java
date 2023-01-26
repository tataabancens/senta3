//package ar.edu.itba.paw.webapp.form.deprecated;
//
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
//
//
//public class ReservationForm {
//
//    @Size(min = 6, max = 50)
//    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
//    private String mail;
//
//    @Size(min = 1, max = 50)
//    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
//    private String name;
//    @Size(min = 8, max = 13)
//    private String phone;
//
//
//    private int hour;
//    private int qPeople;
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getMail() {
//        return mail;
//    }
//
//    public int getHour() {
//        return hour;
//    }
//
//    public void setHour(int hour) {
//        this.hour = hour;
//    }
//
//    public int getqPeople() {
//        return qPeople;
//    }
//
//    public void setqPeople(int qPeople) {
//        this.qPeople = qPeople;
//    }
//}
