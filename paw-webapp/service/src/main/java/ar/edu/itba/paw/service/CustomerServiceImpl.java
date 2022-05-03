package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.persistance.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao;
    private static final int COEFFICIENT = 29;
    private static final float DISCOUNT_COEFFICIENT = 0.90f;

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
        customerDao.addPointsToCustomer(customerId, (int) total / COEFFICIENT);
    }

    @Override
    public Customer create(String customerName, String phone, String mail, long id) {
        return customerDao.create(customerName, phone, mail, id);
    }

    @Override
    public void linkCustomerToUserId(long customerId, long userId) {
        customerDao.linkCustomerToUserId(customerId, userId);
    }

    @Override
    public void updatePoints(long customerId, int points) {
        customerDao.updatePoints(customerId, points);
    }

    public float getDiscountCoefficient() {
        return DISCOUNT_COEFFICIENT;
    }
}
