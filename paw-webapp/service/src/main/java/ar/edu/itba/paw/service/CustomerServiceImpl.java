package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;
    private static final int COEFFICIENT = 71;
    private static final float DISCOUNT_COEFFICIENT = 0.85f;

    @Autowired
    public CustomerServiceImpl(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    @Override
    public Optional<Customer> getCustomerById(long id) {
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

    @Transactional
    @Override
    public Customer create(String customerName, String phone, String mail, long id) {
        return customerDao.create(customerName, phone, mail, id);
    }

    @Transactional
    @Override
    public void addPointsToCustomer(Customer customer, float total) {
        customer.setPoints(customer.getPoints() + (int) total / COEFFICIENT);
    }

    @Transactional
    @Override
    public void linkCustomerToUserId(Customer customer, User user) {
        customer.setUser(user);
    }

    @Transactional
    @Override
    public void updateCustomerData(Customer customer, String name, String phone, String mail) {
        customer.setCustomerName(name);
        customer.setPhone(phone);
        customer.setMail(mail);
    }

    public float getDiscountCoefficient() {
        return DISCOUNT_COEFFICIENT;
    }

    @Override
    public int getPoints(float total) {
        return (int) total / COEFFICIENT;
    }


}
