package ar.edu.itba.paw.model;

public class Customer {
    private long customerId;
    private String customerName;
    private String phone;
    private String mail;
    private int points;
    private long userId;

    public Customer(long customerId, String customerName, String phone, String mail, int points) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone = phone;
        this.mail = mail;
        this.points = points;
    }

    public Customer(long customerId, String customerName, String phone, String mail, long userId, int points) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone = phone;
        this.mail = mail;
        this.points = points;
        this.userId = userId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
