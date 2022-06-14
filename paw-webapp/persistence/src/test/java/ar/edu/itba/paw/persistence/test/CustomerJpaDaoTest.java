package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.persistance.CustomerDao;
import ar.edu.itba.paw.persistance.UserDao;
import ar.edu.itba.paw.persistence.jpa.CustomerJpaDao;
import ar.edu.itba.paw.persistence.jpa.UserJpaDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class CustomerJpaDaoTest {

    private static final long USERID_EXISTS = 1;
    private static final long USERID_NOT_EXISTS = 9999;

    private static final long CUSTOMERID_EXISTS = 1;

    private static final String USERNAME_EXIST = "Juancho";
    private static final String USERNAME_NOT_EXIST = "Juancho el inexistente";

    @Autowired
    private CustomerJpaDao customerDao;

    @Autowired
    private UserJpaDao userDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertCustomer;
    private SimpleJdbcInsert jdbcInsertUser;

    @PersistenceContext
    private EntityManager em;

    //private ReservationJdbcDao reservationDao = new ReservationJdbcDao(null);

    private Number insertCustomer(String customerName, String phone, String mail, long userId){
        Customer newCustomer = new Customer(customerName, phone, mail, userId, 0);
        em.persist(newCustomer);
        return newCustomer.getId();
    }

    private Number insertCustomerNoUser(String customerName, String phone, String mail){
        Customer newCustomer = new Customer(customerName, phone, mail, 0);
        em.persist(newCustomer);
        return newCustomer.getId();
    }

    @Transactional
    Number insertUser(String userName, String pass, Roles role){
        User user = new User(userName, pass, role.getDescription());
        em.persist(user);
        return user.getId();
    }

    @Rollback
    @Test
    public void testGetCustomerById_Exists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerById(USERID_EXISTS);

        // 3. PostCondiciones
        Assert.assertTrue(maybeCustomer.isPresent());
        Assert.assertEquals(USERID_EXISTS, maybeCustomer.get().getId());
    }

    @Rollback
    @Test
    public void testGetCustomerById_NotExists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerById(USERID_NOT_EXISTS);

        // 3. PostCondiciones
        Assert.assertFalse(maybeCustomer.isPresent());
    }

    @Rollback
    @Test
    public void testGetCustomerByUsername(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerByUsername(USERNAME_EXIST);

        // 3. PostCondiciones
        Assert.assertNotNull(maybeCustomer);
    }
//
    @Rollback
    @Test
    public void testGetCustomerByUsername_NotExists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerByUsername(USERNAME_NOT_EXIST);

        // 3. PostCondiciones
        Assert.assertFalse(maybeCustomer.isPresent());
    }

    @Transactional
    @Rollback
    @Test
    public void testCreateCustomer(){
        // 1. Precondiciones

        // 2. Ejercitacion
        //customerDao.create("new name", CUSTOMERID_EXISTS, 15);

        // 3. PostCondiciones
        Optional<Customer> maybeCustomer = Optional.ofNullable(em.find(Customer.class, CUSTOMERID_EXISTS));
        Assert.assertTrue(maybeCustomer.isPresent());
        Assert.assertEquals(CUSTOMERID_EXISTS, maybeCustomer.get().getId());
        Assert.assertEquals(15, maybeCustomer.get().getPoints());
    }
}
