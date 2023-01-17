package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.persistence.jpa.CustomerJpaDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class CustomerJpaDaoTest {

    private static final long USERID_EXISTS = 1;
    private static final long USERID_NOT_EXISTS = 9999;

    private static final String USERNAME_EXIST = "Juancho";
    private static final String USERNAME_NOT_EXIST = "Juancho el inexistente";

    @Autowired
    private CustomerJpaDao customerDao;


    @Rollback
    @Test
    public void testGetCustomerById_Exists() {
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerById(USERID_EXISTS);

        // 3. PostCondiciones
        Assert.assertTrue(maybeCustomer.isPresent());
        Assert.assertEquals(USERID_EXISTS, maybeCustomer.get().getId());
    }

    @Rollback
    @Test
    public void testGetCustomerById_NotExists() {
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerById(USERID_NOT_EXISTS);

        // 3. PostCondiciones
        Assert.assertFalse(maybeCustomer.isPresent());
    }

    @Rollback
    @Test
    public void testGetCustomerByUsername() {
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerByUsername(USERNAME_EXIST);

        // 3. PostCondiciones
        Assert.assertNotNull(maybeCustomer);
    }

    //
    @Rollback
    @Test
    public void testGetCustomerByUsername_NotExists() {
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Customer> maybeCustomer = customerDao.getCustomerByUsername(USERNAME_NOT_EXIST);

        // 3. PostCondiciones
        Assert.assertFalse(maybeCustomer.isPresent());
    }
}
