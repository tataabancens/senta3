package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getCustomerById(long id);

    Optional<Customer> getCustomerByUsername(String username);

    Customer create(String customerName, String phone, String mail);

    void addPointsToCustomer(Customer customer, float total);

    Customer create(String customerName, String phone, String mail, long id);

    void linkCustomerToUserId(Customer customer, User user);

    float getDiscountCoefficient();

    int getPoints(float total);

    boolean patchCustomer(long id, String name, String phone, String mail, Long userId, Integer points);

    boolean deleteCustomer(long id);

    List<Customer> getCustomers(int page);
}
