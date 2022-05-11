package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getUserByID(long id);

    Optional<Customer> getCustomerByUsername(String username);

    Customer create(String customerName, String phone, String mail);

    void addPointsToCustomer(long customerId, float total);

    Customer create(String customerName, String phone, String mail, long id);

    void linkCustomerToUserId(long customerId, long userId);

    //void updatePoints(long customerId, int points);

    public float getDiscountCoefficient();

    int getPoints(float total);

    void updateCustomerData(long customerId, String name, String phone, String mail);
}
