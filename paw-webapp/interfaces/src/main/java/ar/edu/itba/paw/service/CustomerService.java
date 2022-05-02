package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getUserByID(long id);

    Optional<Customer> getCustomerByUsername(String username);

    Customer create(String customerName, String phone, String mail);
}
