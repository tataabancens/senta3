package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Customer;

import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> getCustomerById(long id);

    Customer create(String customerName, String phone, String mail);

    Optional<Customer> getCustomerByUsername(String username);

    void addPointsToCustomer(long customerId, int points);

    Customer create(String customerName, String phone, String mail, long id);

    void linkCustomerToUserId(long customerId, long userId);

    void updateCustomerData(long customerId, String name, String phone, String mail);
}
