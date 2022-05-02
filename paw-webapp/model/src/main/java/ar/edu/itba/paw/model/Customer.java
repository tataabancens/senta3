package ar.edu.itba.paw.model;

public class Customer {
    private long customerId;
    private String customerName;
    private String Phone;
    private String Mail;
    private int points;

    public Customer(long customerId, String customerName, String phone, String mail, int points) {
        this.customerId = customerId;
        this.customerName = customerName;
        Phone = phone;
        Mail = mail;
        this.points = points;
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
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
