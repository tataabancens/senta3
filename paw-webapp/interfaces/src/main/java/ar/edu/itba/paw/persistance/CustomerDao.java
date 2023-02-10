package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> getCustomerById(long id);

    Customer create(String customerName, String phone, String mail);

    Optional<Customer> getCustomerByUsername(String username);

    Customer create(String customerName, String phone, String mail, long id);

    List<Customer> getCustomers(int page);
}
