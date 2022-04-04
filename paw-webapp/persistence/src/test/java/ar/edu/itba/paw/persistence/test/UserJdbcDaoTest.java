package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.jdbc.JdbcTestUtils;


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
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" +
                "userId INTEGER IDENTITY PRIMARY KEY," +
                "username varchar(100)," +
                "password varchar(100)" +
                ")");
    }

    @Test
    public void testCreateUser(){
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");

        // 2. Ejercitacion
        User user = userDao.create("pepe", "pass");

        // 3. PostCondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals("pepe", user.getUsername());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testFindUserByIdDoesntExist(){
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(1);

        // 3. PostCondiciones
        Assert.assertFalse(maybeUser.isPresent());

    }

    @Test
    public void testFindUserByIdExists(){
        // 1. Precondiciones
        Map<String, String> map = new HashMap<>();
        map.put("username", "pepe");

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        Number key = jdbcInsert.executeAndReturnKey(map);

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(key.longValue());

        // 3. PostCondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals("pepe", maybeUser.get().getUsername());
    }
}
