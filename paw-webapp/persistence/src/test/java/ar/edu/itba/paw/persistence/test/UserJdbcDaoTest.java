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
import org.springframework.jdbc.core.RowMapper;
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
    private static final String CUSTOMER_TABLE = "customer";
    private static final String DISH_TABLE = "dish";
    private static final String RESERVATION_TABLE = "reservation";
    private static final String ORDER_ITEM_TABLE = "orderItem";



    private UserJdbcDao userDao ;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertUser;
    private SimpleJdbcInsert jdbcInsertRestaurant;
    private SimpleJdbcInsert jdbcInsertCustomer;



    @Autowired
    private DataSource ds;

    @Before
    public void setUp(){
        userDao = new UserJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertUser = new SimpleJdbcInsert(ds)
                .withTableName(USER_TABLE)
                .usingGeneratedKeyColumns("userId");
        jdbcInsertRestaurant = new SimpleJdbcInsert(ds)
                .withTableName(RESTAURANT_TABLE)
                .usingGeneratedKeyColumns("restaurantId");
        jdbcInsertCustomer = new SimpleJdbcInsert(ds)
                .withTableName(CUSTOMER_TABLE)
                .usingGeneratedKeyColumns("customerid");
    }

    private Number insertUser(String userName, String pass, Roles role){
        final Map<String, Object> userData = new HashMap<>();
        userData.put("userName", userName);
        userData.put("pass", pass);
        userData.put("role", role);

        Number userId = jdbcInsertUser.executeAndReturnKey(userData);
        return userId;
    }

    private void cleanAllTables(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESERVATION_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, DISH_TABLE);
        //JdbcTestUtils.deleteFromTables(jdbcTemplate, RESTAURANT_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, CUSTOMER_TABLE);
        //JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
        jdbcTemplate.execute("DELETE FROM users WHERE userId NOT IN ( 1 )");
    }

    private static final RowMapper<User> ROW_MAPPER = (resultSet, i) ->
            new User(resultSet.getLong("userId"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"));

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
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, USER_TABLE));
    }

    @Test
    @Rollback
    public void testFindUserById_DoesntExist(){
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(100);

        // 3. PostCondiciones
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    @Rollback
    public void testFindUserById_Exists(){
        // 1. Precondiciones
        cleanAllTables();
        Number userId = insertUser(USERNAME, "passguord", Roles.CUSTOMER);

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(userId.longValue());

        // 3. PostCondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(USERNAME, maybeUser.get().getUsername());
    }

    @Test
    @Rollback
    public void testFindUserByName_Exists(){
        // 1. Precondiciones
        cleanAllTables();
        Number userId = insertUser(USERNAME, "passguord", Roles.CUSTOMER);

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.findByName(USERNAME);

        // 3. PostCondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(USERNAME, maybeUser.get().getUsername());
    }

    @Test
    @Rollback
    public void testFindUserByName_NotExists(){
        // 1. Precondiciones
        cleanAllTables();
        Number userId = insertUser(USERNAME, "passguord", Roles.CUSTOMER);

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.findByName("Not_Username");

        // 3. PostCondiciones
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    @Rollback
    public void testupdateUsername(){
        // 1. Precondiciones
        cleanAllTables();
        Number userId = insertUser(USERNAME, "passguord", Roles.CUSTOMER);

        // 2. Ejercitacion
        userDao.updateUsername(USERNAME, "new_username");

        // 3. PostCondiciones
        Optional<User> user = jdbcTemplate.query("SELECT * FROM users WHERE username = ?", new Object[]{"new_username"}, ROW_MAPPER).stream().findFirst();
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals("new_username", user.get().getUsername());
        Assert.assertEquals(userId.longValue(), user.get().getId());
    }
}
