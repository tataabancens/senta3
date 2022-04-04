package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


import java.util.Optional;

public class UserJdbcDaoTest {

    private UserJdbcDao userDao ;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("user");
        ds.setPassword("");
        userDao = new UserJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds)
    }

    @Test
    public void testCreateUser(){
        User user = userDao.create("pepe", "pass");
        Assert.assertNotNull(user);
        Assert.assertEquals("pepe", user.getUsername());
    }

    @Test
    public void testUserDoesntExist(){
        

        Optional<User> maybeUser = userDao.getUserById(1);

        Assert.assertFalse(maybeUser.isPresent());

    }

    @Test
    public void testUserExists(){

    }
}
