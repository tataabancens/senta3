package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistence.CustomerJdbcDao;
import ar.edu.itba.paw.persistence.ReservationJdbcDao;
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

    private CustomerJdbcDao customerDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertCustomer;

    @Autowired
    private DataSource ds;


    //private ReservationJdbcDao reservationDao = new ReservationJdbcDao(null);

    @Before
    public void setUp(){
        customerDao = new CustomerJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertCustomer = new SimpleJdbcInsert(ds)
                .withTableName(CUSTOMER_TABLE)
                .usingGeneratedKeyColumns("customerId");
    }

    public Number insertCustomer(String customerName, String phone, String mail){
        final Map<String, Object> customerData = new HashMap<>();
        customerData.put("customerName", customerName);
        customerData.put("Phone", phone);
        customerData.put("Mail", mail);

        Number customerId = jdbcInsertCustomer.executeAndReturnKey(customerData);
        return customerId;
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
    public void testGetCsutomerById_NotExists(){
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

        // 2. Ejercitacion

        // 3. PostCondiciones

    }

    @Test
    @Rollback
    public void testUpdatePoints(){
        // 1. Precondiciones

        // 2. Ejercitacion

        // 3. PostCondiciones

    }

    @Test
    @Rollback
    public void testUpdateCustomerData(){
        // 1. Precondiciones

        // 2. Ejercitacion

        // 3. PostCondiciones

    }

}
