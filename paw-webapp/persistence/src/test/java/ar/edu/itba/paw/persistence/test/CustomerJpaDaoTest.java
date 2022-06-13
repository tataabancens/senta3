package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Roles;
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
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class CustomerJpaDaoTest {

    private static final String RESTAURANT_TABLE = "restaurant";
    private static final String USER_TABLE = "users";
    private static final String CUSTOMER_TABLE = "customer";
    private static final String DISH_TABLE = "dish";
    private static final String RESERVATION_TABLE = "reservation";
    private static final String ORDER_ITEM_TABLE = "orderItem";


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

//    private void cleanAllTables(){
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESERVATION_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, DISH_TABLE);
//        //JdbcTestUtils.deleteFromTables(jdbcTemplate, RESTAURANT_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, CUSTOMER_TABLE);
//        //JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
//        jdbcTemplate.execute("DELETE FROM users WHERE userId NOT IN ( 1 )");
//    }

    @Rollback
    @Test
    public void testGetCustomerById_Exists(){
        // 1. Precondiciones
        //cleanAllTables();
        //Number userId = insertUser("username", "pass", Roles.CUSTOMER);
        //Number customerId = insertCustomer("Pepe", "123456789", "pepe@gmail.com", userId.intValue());
        //User user = userDao.create("username", "pass", Roles.CUSTOMER);
        //User user = new User("username", "pass", Roles.CUSTOMER.getDescription());
//        Customer customer = new Customer("Juancho", "123456789", "juan@gmail.com", 2, 0);
//        long customerId = customer.getId();


        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerById(1);

        // 3. PostCondiciones
        Assert.assertTrue(maybeCustomer.isPresent());
        Assert.assertEquals(1, maybeCustomer.get().getId());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, CUSTOMER_TABLE));
    }

//    @Rollback
//    @Test
//    public void testGetCustomerById_NotExists(){
//        // 1. Precondiciones
//        //cleanAllTables();
//        Number userId = insertUser("username", "pass", Roles.CUSTOMER);
//        Number customerId = insertCustomer("Pepe", "123456789", "pepe@gmail.com", userId.intValue());
//
//        // 2. Ejercitacion
//        Optional<Customer> maybeCustomer = customerDao.getCustomerById(customerId.longValue()+1);
//
//        // 3. PostCondiciones
//        Assert.assertFalse(maybeCustomer.isPresent());
//    }
//
//    @Rollback
//    @Test
//    public void testGetCustomerByUsername(){
//        // 1. Precondiciones
//        //cleanAllTables();
//        Number userId = insertUser("username", "pass", Roles.CUSTOMER);
//        Number customerId = insertCustomer("Pepe", "123456789", "pepe@gmail.com", userId.intValue());
//
//        // 2. Ejercitacion
//        Optional<Customer> maybeCustomer = customerDao.getCustomerByUsername("Pepito");
//
//        // 3. PostCondiciones
//        Assert.assertNotNull(maybeCustomer);
//    }
//
//    @Rollback
//    @Test
//    public void testGetCustomerByUsername_NotExists(){
//        // 1. Precondiciones
//        //cleanAllTables();
//        //insertCustomer("Pepito", "123456789", "pepe@gmail.com");
//
//        // 2. Ejercitacion
//        Optional<Customer> maybeCustomer = customerDao.getCustomerByUsername("Pepitooooo");
//
//        // 3. PostCondiciones
//        Assert.assertFalse(maybeCustomer.isPresent());
//    }
//
//    @Rollback
//    @Test
//    public void testAddPointsToCustomer(){
//        // 1. Precondiciones
//        //cleanAllTables();
//        Number userId = insertUser("username", "pass", Roles.CUSTOMER);
//        Number customerId = insertCustomer("Pepe", "123456789", "pepe@gmail.com", userId.intValue());
//
//        // 2. Ejercitacion
//        customerDao.addPointsToCustomer(customerId.longValue(), 15);
//
//        // 3. PostCondiciones
//        //Optional<Customer> customer = jdbcTemplate.query("SELECT * FROM customer WHERE customerId = ?", new Object[]{customerId.longValue()}, ROW_MAPPER).stream().findFirst();
//        Optional<Customer> customer = em.createQuery("SELECT * FROM customer WHERE customerId = 1");
//        Assert.assertTrue(customer.isPresent());
//        Assert.assertEquals(customerId.longValue(), customer.get().getId());
//        Assert.assertEquals(15, customer.get().getPoints());
//
//    }
//
//    @Rollback
//    @Test
//    public void testAddPoints() {
//        // 1. Precondiciones
//        //cleanAllTables();
//        Number userId = insertUser("username", "pass", Roles.CUSTOMER);
//        Number customerId = insertCustomer("Pepe", "123456789", "pepe@gmail.com", userId.intValue());
//
//        // 2. Ejercitacion
//        customerDao.addPointsToCustomer(customerId.longValue(), 30);
//
//        // 3. PostCondiciones
//        Optional<Customer> customer = jdbcTemplate.query("SELECT * FROM customer WHERE customerId = ?", new Object[]{customerId.longValue()}, ROW_MAPPER).stream().findFirst();
//        Assert.assertTrue(customer.isPresent());
//        Assert.assertEquals(customerId.longValue(), customer.get().getId());
//        Assert.assertEquals(30, customer.get().getPoints());
//
//    }
//
//    @Rollback
//    @Test
//    public void testLinkCustomerToUserId(){
//        // 1. Precondiciones
//        //cleanAllTables();
//        Number user = insertUser("username", "pass", Roles.CUSTOMER);
//        Number user2 = insertUser("username2", "pass", Roles.CUSTOMER);
//        Number customerId = insertCustomer("PepeCapo", "123456789", "pepe@gmail.com", user.longValue());
//
//        // 2. Ejercitacion
//        customerDao.linkCustomerToUserId(customerId.longValue(), user2.longValue());
//
//        // 3. PostCondiciones
//        Optional<Customer> customer = jdbcTemplate.query("SELECT * FROM customer WHERE customerId = ?", new Object[]{customerId.longValue()}, ROW_MAPPER_WITH_USERID).stream().findFirst();
//        Assert.assertTrue(customer.isPresent());
//        Assert.assertEquals(customerId.longValue(), customer.get().getId());
//        Assert.assertEquals(user2.longValue(), customer.get().getUserId());
//    }
//
//    @Rollback
//    @Test
//    public void testUpdateCustomerData(){
//        // 1. Precondiciones
//        //cleanAllTables();
//        Number userId = insertUser("username", "pass", Roles.CUSTOMER);
//        Number customerId = insertCustomer("Pepe", "123456789", "pepe@gmail.com", userId.longValue());
//
//        // 2. Ejercitacion
//        customerDao.updateCustomerData(customerId.longValue(), "pepito", "789456789", "elpepe@gmail.com");
//
//        // 3. PostCondiciones
//        Optional<Customer> customer = jdbcTemplate.query("SELECT * FROM customer WHERE customerId = ?", new Object[]{customerId.longValue()}, ROW_MAPPER_WITH_USERID).stream().findFirst();
//        Assert.assertTrue(customer.isPresent());
//        Assert.assertEquals(customerId.longValue(), customer.get().getId());
//        Assert.assertEquals("pepito", customer.get().getCustomerName());
//    }

}
