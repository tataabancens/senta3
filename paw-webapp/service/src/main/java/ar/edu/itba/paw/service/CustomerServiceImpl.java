package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.persistance.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;
    private static final int COEFFICIENT = 71;
    private static final float DISCOUNT_COEFFICIENT = 0.90f;

    @Autowired
    public CustomerServiceImpl(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    @Override
    public Optional<Customer> getUserByID(long id) {
        return customerDao.getCustomerById(id);
    }

    @Transactional
    @Override
    public Customer create(String customerName, String phone, String mail) {
        return customerDao.create(customerName, phone, mail);
    }

    @Transactional
    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        return customerDao.getCustomerByUsername(username);
    }

    @Override
    public void addPointsToCustomer(long customerId, float total) {
        customerDao.addPointsToCustomer(customerId, getPoints(total));
    }

    @Transactional
    @Override
    public Customer create(String customerName, String phone, String mail, long id) {
        return customerDao.create(customerName, phone, mail, id);
    }

    @Override
    public void linkCustomerToUserId(long customerId, long userId) {
        customerDao.linkCustomerToUserId(customerId, userId);
    }
/*
    @Override
    public void updatePoints(long customerId, int points) {
        customerDao.updatePoints(customerId, points);
    }

 */

    public float getDiscountCoefficient() {
        return DISCOUNT_COEFFICIENT;
    }

    @Override
    public int getPoints(float total) {
        return (int) total / COEFFICIENT;
    }

    @Override
    public void updateCustomerData(long customerId, String name, String phone, String mail) {
        customerDao.updateCustomerData(customerId, name, phone, mail);
    }
}
