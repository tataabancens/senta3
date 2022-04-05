package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getUserByID(long id);

    Customer create(String customerName, String phone, String mail);
}
