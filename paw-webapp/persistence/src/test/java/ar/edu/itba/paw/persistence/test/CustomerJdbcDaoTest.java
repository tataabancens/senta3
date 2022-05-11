package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.persistence.CustomerJdbcDao;
import ar.edu.itba.paw.persistence.ReservationJdbcDao;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class CustomerJdbcDaoTest {

    private static final String CUSTOMER_TABLE = "customer";
    private static final String USER_TABLE = "user";

    private CustomerJdbcDao customerDao;
    private UserJdbcDao userDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertCustomer;
    private SimpleJdbcInsert jdbcInsertUser;

    @Autowired
    private DataSource ds;


    //private ReservationJdbcDao reservationDao = new ReservationJdbcDao(null);

    @Before
    public void setUp(){
        customerDao = new CustomerJdbcDao(ds);
        userDao = new UserJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertCustomer = new SimpleJdbcInsert(ds)
                .withTableName(CUSTOMER_TABLE)
                .usingGeneratedKeyColumns("customerId");
        jdbcInsertUser = new SimpleJdbcInsert(ds)
                .withTableName(USER_TABLE)
                .usingGeneratedKeyColumns("userId");
    }

    public Number insertCustomer(String customerName, String phone, String mail){
        final Map<String, Object> customerData = new HashMap<>();
        customerData.put("customerName", customerName);
        customerData.put("Phone", phone);
        customerData.put("Mail", mail);

        Number customerId = jdbcInsertCustomer.executeAndReturnKey(customerData);
        return customerId;
    }

    public Number insertUser(String userName, String pass, Roles role){
        final Map<String, Object> userData = new HashMap<>();
        userData.put("userName", userName);
        userData.put("pass", pass);
        userData.put("role", role);

        Number userId = jdbcInsertUser.executeAndReturnKey(userData);
        return userId;
    }

    public void cleanAllTables(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, CUSTOMER_TABLE);
    }

    @Test
    @Rollback
    public void testGetCustomerById_Exists(){
        // 1. Precondiciones
        cleanAllTables();
        Number customerId = insertCustomer("Pepe", "123456789", "pepe@gmail.com");

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerById(customerId.longValue());

        // 3. PostCondiciones
        Assert.assertTrue(maybeCustomer.isPresent());
        Assert.assertEquals(customerId.longValue(), maybeCustomer.get().getCustomerId());
    }

    @Test
    @Rollback
    public void testGetCustomerById_NotExists(){
        // 1. Precondiciones
        cleanAllTables();
        Number customerId = insertCustomer("Pepito", "123456789", "pepe@gmail.com");

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerById(customerId.longValue()+1);

        // 3. PostCondiciones
        Assert.assertFalse(maybeCustomer.isPresent());
    }

    @Test
    @Rollback
    public void testGetCustomerByUsername(){
        // 1. Precondiciones
        cleanAllTables();
        insertCustomer("Pepito", "123456789", "pepe@gmail.com");

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerByUsername("Pepito");

        // 3. PostCondiciones
        Assert.assertNotNull(maybeCustomer);
    }

    @Test
    @Rollback
    public void testAddPointsToCustomer(){
        // 1. Precondiciones
        cleanAllTables();
        Number customer = insertCustomer("Pepito", "123456789", "pepe@gmail.com");

        // 2. Ejercitacion
        customerDao.addPointsToCustomer(customer.longValue(), 15);

        // 3. PostCondiciones
        // TODO seguir esto ??
    }

    @Test
    @Rollback
    public void testLinkCustomerToUserId(){
        // 1. Precondiciones
        cleanAllTables();
        Number customer = insertCustomer("Pepote", "123456789", "pepe@gmail.com");
        Number user = insertUser("Pepote", "123", Roles.CUSTOMER);

        // 2. Ejercitacion
        customerDao.linkCustomerToUserId(customer.longValue(), user.longValue());

        // 3. PostCondiciones
        Assert.assertEquals(user.longValue(), customer.longValue());

    }

    @Test
    @Rollback
    public void testUpdatePoints() {
        // 1. Precondiciones
        cleanAllTables();
        Number customer = insertCustomer("Pepote", "123456789", "pepe@gmail.com");

        // 2. Ejercitacion
        customerDao.updatePoints(customer.longValue(), 30);

        // 3. PostCondiciones

    }

    @Test
    @Rollback
    public void testUpdateCustomerData(){
        // 1. Precondiciones
        cleanAllTables();
        Number customer = insertCustomer("Pepote", "123456789", "pepe@gmail.com");

        // 2. Ejercitacion
        customerDao.updateCustomerData(customer.longValue(), "pepito", "789456789", "elpepe@gmail.com");

        // 3. PostCondiciones

    }

}
