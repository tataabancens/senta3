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

    void updateCustomerData(Customer customer, String name, String phone, String mail);
}
