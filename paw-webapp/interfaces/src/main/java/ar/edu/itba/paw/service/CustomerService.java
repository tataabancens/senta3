package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getCustomerById(long id);

    Optional<Customer> getCustomerByUsername(String username);

    Customer create(String customerName, String phone, String mail);

    void addPointsToCustomer(Customer customer, float total);

    Customer create(String customerName, String phone, String mail, long id);

    void linkCustomerToUserId(Customer customer, User user);

    public float getDiscountCoefficient();

    int getPoints(float total);

    void updateCustomerDataByUsername(String username, String name, String phone, String mail);

    void updateCustomerMailByUsername(String username, String mail);

    void updateCustomerPhoneByUsername(String username, String phone);

    void updateCustomerNameByUsername(String username, String name);
}
