package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import com.sun.javafx.collections.MappingChange;
import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.jdbc.JdbcTestUtils;
import sun.java2d.pipe.SpanShapeRenderer;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserJdbcDaoTest {

    private UserJdbcDao userDao ;
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Before
    public void setUp(){
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("user");
        ds.setPassword("");
        userDao = new UserJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds);
    }

    @Test
    public void testCreateUser(){
        User user = userDao.create("pepe", "pass");
        Assert.assertNotNull(user);
        Assert.assertEquals("pepe", user.getUsername());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testUserDoesntExist(){

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");

        Optional<User> maybeUser = userDao.getUserById(1);

        Assert.assertFalse(maybeUser.isPresent());

    }

    @Test
    public void testUserExists(){

        Map<String, String> map = new HashMap<>();
        map.put("username", "pepe");

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        jdbcInsert.executeAndReturnKey(map);

        Optional<User> maybeUser = userDao.getUserById(1);

        Assert.assertFalse(maybeUser.isPresent());
        Assert.assertEquals("pepe", maybeUser.get().getUsername());
    }
}
