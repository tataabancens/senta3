package ar.edu.itba.paw.model;

public class Customer {
    private long customerId;
    private String customerName;
    private String Phone;
    private String Mail;

    public Customer(long customerId, String customerName, String phone, String mail) {
        this.customerId = customerId;
        this.customerName = customerName;
        Phone = phone;
        Mail = mail;
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
}
