package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.CustomerDao;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;
    private final UserDao userDao;
//    private final UserService us;

    private static final int COEFFICIENT = 71;
    private static final float DISCOUNT_COEFFICIENT = 0.85f;

//    @Autowired
//    private UserService us;

    @Autowired
    public CustomerServiceImpl(final CustomerDao customerDao, UserDao userDao){//}, final UserService us) {//
        this.customerDao = customerDao;
        this.userDao = userDao;
//        this.us = us;
    }

    @Transactional
    @Override
    public Optional<Customer> getCustomerById(long id) {
        return customerDao.getCustomerById(id);
    }

    @Transactional
    @Override
    public Customer create(String customerName, String phone, String mail, Optional<User> user) {
        if(user.isPresent() && getCustomerByUsername(user.get().getUsername()).isPresent()){ //userId already has a customer
            return null;
        }
        Customer customer = customerDao.create(customerName, phone, mail);
        if(!user.isPresent()){ // only create
            return customer;
        }
        user.ifPresent(customer::setUser); //and link
        return customer;
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
    public boolean patchCustomer(long id, String name, String phone, String mail, Long userId) {
        Optional<Customer> maybeCustomer = getCustomerById(id);
        if(!maybeCustomer.isPresent()){
            return false;
        }
        Customer customer = maybeCustomer.get();
        if(name != null){
            customer.setCustomerName(name);
        }
        if(phone != null){
            customer.setPhone(phone);
        }
        if(mail != null){
            customer.setMail(mail);
        }
        if(userId != null){
            Optional<User> maybeUser = userDao.getUserById(userId);
            if(!maybeUser.isPresent()){
                return false;
            }
            customer.setUser(maybeUser.get());
        }
        return true;
    }

    @Transactional
    @Override
    public boolean patchCustomerPoints(long id, int points) {
        Optional<Customer> maybeCustomer = getCustomerById(id);
        if(!maybeCustomer.isPresent()){
            return false;
        }
        maybeCustomer.get().setPoints(points);
        return true;
    }

    @Override
    public float getDiscountCoefficient() {
        return DISCOUNT_COEFFICIENT;
    }

    @Override
    public int getPoints(float total) {
        return (int) total / COEFFICIENT;
    }

    @Override
    public List<Customer> getCustomers(int page){
        return customerDao.getCustomers(page);
    }
}
