package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserJdbcDaoTest {

    private static final String USER_TABLE = "users";
    private static final String USERNAME = "pepe";

    private UserJdbcDao userDao ;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    private DataSource ds;

    @Before
    public void setUp(){
        userDao = new UserJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(USER_TABLE)
                .usingGeneratedKeyColumns("userId");
    }

    @Test
    public void testCreateUser(){
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);

        // 2. Ejercitacion
        User user = userDao.create(USERNAME, "pass");

        // 3. PostCondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USER_TABLE));
    }

    @Test
    public void testFindUserByIdDoesntExist(){
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(1);

        // 3. PostCondiciones
        Assert.assertFalse(maybeUser.isPresent());

    }

    @Test
    public void testFindUserByIdExists(){
        // 1. Precondiciones
        Map<String, String> map = new HashMap<>();
        map.put("username", USERNAME);

        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
        Number key = jdbcInsert.executeAndReturnKey(map);

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(key.longValue());

        // 3. PostCondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(USERNAME, maybeUser.get().getUsername());
    }
}