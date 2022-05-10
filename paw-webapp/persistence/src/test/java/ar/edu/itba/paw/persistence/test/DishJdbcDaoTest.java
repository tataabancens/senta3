package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistence.DishJdbcDao;
import ar.edu.itba.paw.persistence.ReservationJdbcDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

import static ar.edu.itba.paw.model.enums.DishCategory.MAIN_DISH;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class DishJdbcDaoTest {
    private static final String RESERVATION_TABLE = "reservation";
    private static final String ORDER_ITEM_TABLE = "orderItem";
    private static final String CUSTOMER_TABLE = "customer";
    private static final String RESTAURANT_TABLE = "restaurant";
    private static final String DISH_TABLE = "dish";

    private ReservationJdbcDao reservationDao;
    private DishJdbcDao dishDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertReservation;
    private SimpleJdbcInsert jdbcInsertOrderItem;
    private SimpleJdbcInsert jdbcInsertCustomer;
    private SimpleJdbcInsert jdbcInsertRestaurant;
    private SimpleJdbcInsert jdbcInsertDish;

    @Autowired
    private DataSource ds;

    @Before
    public void setUp(){
        dishDao = new DishJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertRestaurant = new SimpleJdbcInsert(ds)
                .withTableName(RESTAURANT_TABLE)
                .usingGeneratedKeyColumns("restaurantId");
        jdbcInsertReservation = new SimpleJdbcInsert(ds)
                .withTableName(RESERVATION_TABLE)
                .usingGeneratedKeyColumns("reservationId");
        jdbcInsertCustomer = new SimpleJdbcInsert(ds)
                .withTableName(CUSTOMER_TABLE)
                .usingGeneratedKeyColumns("customerId");
        jdbcInsertDish = new SimpleJdbcInsert(ds)
                .withTableName(DISH_TABLE)
                .usingGeneratedKeyColumns("dishId");
        jdbcInsertOrderItem = new SimpleJdbcInsert(ds)
                .withTableName(ORDER_ITEM_TABLE)
                .usingGeneratedKeyColumns("orderItemId");
    }


    public Number insertDish(String name, String description, int price, int restaurantId, int imageId, DishCategory category){
        final Map<String, Object> dishData = new HashMap<>();
        dishData.put("dishName", name);
        dishData.put("dishDescription", description);
        dishData.put("price", price);
        dishData.put("restaurantId", restaurantId);
        dishData.put("imageId", imageId);
        dishData.put("category", category);
        Number dishId = jdbcInsertDish.executeAndReturnKey(dishData);
        return dishId;
    }

    public Number insertReservation(int restaurantId, int reservationHour, int customerId, int reservationStatus, int qPeople){
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurantId", restaurantId);
        reservationData.put("reservationHour", reservationHour);
        reservationData.put("customerId", customerId);
        reservationData.put("reservationstatus", reservationStatus);
        reservationData.put("qPeople", 1);
        reservationData.put("reservationdiscount", false);
        reservationData.put("startedAtTime", null);
        Number reservationId = jdbcInsertReservation.executeAndReturnKey(reservationData);
        return reservationId;
    }

    public Number insertOrderItem(int dishId, int reservationId, int unitPrice, int qty, int status){
        final Map<String, Object> orderItemData = new HashMap<>();
        orderItemData.put("dishid", dishId);
        orderItemData.put("reservationid", reservationId);
        orderItemData.put("unitprice", unitPrice);
        orderItemData.put("quantity", qty);
        orderItemData.put("status", status);
        Number orderItemId = jdbcInsertOrderItem.executeAndReturnKey(orderItemData);
        return orderItemId;
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
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESERVATION_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, DISH_TABLE);
    }

    @Test
    @Rollback
    public void testGetDishById() {
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);

        // 2. Ejercitacion
        Optional<Dish> dish = dishDao.getDishById(dishId.longValue());

        // 3. PostCondiciones
        Assert.assertTrue(dish.isPresent());
        Assert.assertEquals(dishId.longValue(), dish.get().getId());
    }

    @Test
    @Rollback
    public void testGetDishById_invalid() {
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);

        // 2. Ejercitacion
        Optional<Dish> dish = dishDao.getDishById(dishId.longValue()+1);

        // 3. PostCondiciones
        Assert.assertFalse(dish.isPresent());
    }

    @Test
    @Rollback
    public void testCreateDish() {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitacion
        Dish dish = dishDao.create(1, "Empanada", "sin pasas de uva", 100, 1, MAIN_DISH);

        // 3. PostCondiciones
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, DISH_TABLE));
    }

    @Test
    @Rollback
    public void testGetRecommendedDish() { //le falta la implementaciooon y distintos casos
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);

        // 2. Ejercitacion
        Optional<Dish> dish = dishDao.getRecommendedDish(reservationId.longValue());

        // 3. PostCondiciones
        Assert.assertTrue(dish.isPresent());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, DISH_TABLE));
    }

}
