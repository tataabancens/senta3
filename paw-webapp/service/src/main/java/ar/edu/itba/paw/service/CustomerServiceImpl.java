package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao;
    private static final int coeficient = 29;

    @Autowired
    public CustomerServiceImpl(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Optional<Customer> getUserByID(long id) {
        return customerDao.getCustomerById(id);
    }

    @Override
    public Customer create(String customerName, String phone, String mail) {
        return customerDao.create(customerName, phone, mail);
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        return customerDao.getCustomerByUsername(username);
    }

    @Override
    public void addPointsToCustomer(long customerId, float total) {
        customerDao.addPointsToCustomer(customerId, (int) total / 29);
    }
}
