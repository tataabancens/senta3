package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
public class UserJdbcDaoTest {

    private static final String USER_TABLE = "users";
    private static final String USERNAME = "pepe";
    private static final String RESTAURANT_TABLE = "restaurant";


    private UserJdbcDao userDao ;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private SimpleJdbcInsert jdbcInsertRestaurant;


    @Autowired
    private DataSource ds;

    @Before
    public void setUp(){
        userDao = new UserJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(USER_TABLE)
                .usingGeneratedKeyColumns("userId");
        jdbcInsertRestaurant = new SimpleJdbcInsert(ds)
                .withTableName(RESTAURANT_TABLE)
                .usingGeneratedKeyColumns("restaurantId");
    }
    public void cleanAllTables(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESTAURANT_TABLE);
    }

    @Test
    @Rollback
    public void testCreateUser(){
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitacion
        User user = userDao.create(USERNAME, "pass", Roles.CUSTOMER);

        // 3. PostCondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USER_TABLE));
    }

    @Test
    @Rollback
    public void testFindUserById_DoesntExist(){
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(1);

        // 3. PostCondiciones
        Assert.assertFalse(maybeUser.isPresent());

    }

    @Test
    @Rollback
    public void testFindUserById_Exists(){
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
