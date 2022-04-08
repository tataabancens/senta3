package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.ReservationDao;
import ar.edu.itba.paw.persistence.ReservationJdbcDao;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReservationJdbcDaoTest {

    private static final String RESERVATION_TABLE = "reservation";
    private static final String ORDER_ITEM_TABLE = "orderItem";
//    private static final String CUSTOMER_TABLE = "customer";
//    private static final String RESTAURANT_TABLE = "restaurant";
//    private static final String DISH_TABLE = "dish";

    private ReservationDao reservationDao ;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertReservation;
    private SimpleJdbcInsert jdbcInsertOrderItem;
//    private SimpleJdbcInsert jdbcInsertCustomer;
//    private SimpleJdbcInsert jdbcInsertRestaurant;
//    private SimpleJdbcInsert jdbcInsertDish;

    @Autowired
    private DataSource ds;

    @Before
    public void setUp(){
        reservationDao = new ReservationJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertReservation = new SimpleJdbcInsert(ds)
                .withTableName(RESERVATION_TABLE)
                .usingGeneratedKeyColumns("reservationId");
//        jdbcInsertCustomer = new SimpleJdbcInsert(ds)
//                .withTableName(CUSTOMER_TABLE)
//                .usingGeneratedKeyColumns("customerId");
//        jdbcInsertRestaurant = new SimpleJdbcInsert(ds)
//                .withTableName(RESTAURANT_TABLE)
//                .usingGeneratedKeyColumns("restaurantId");
//        jdbcInsertDish = new SimpleJdbcInsert(ds)
//                .withTableName(DISH_TABLE)
//                .usingGeneratedKeyColumns("dishId");
        jdbcInsertOrderItem = new SimpleJdbcInsert(ds)
                .withTableName(ORDER_ITEM_TABLE);
    }

    @Test
    public void testGetOrderItemsByReservationIdExists() {
        // 1. Precondiciones
        List<OrderItem> testList = new ArrayList<>();
        testList.add(new OrderItem(1, 1, 890, 3, 0));
        testList.add(new OrderItem(1, 2, 650, 3, 0));

        // 2. Ejercitacion
        List<OrderItem> maybeList = reservationDao.getOrderItemsByReservationId(1);

        // 3. PostCondiciones
        Assert.assertFalse(maybeList.isEmpty());
        for (int i = 0; i < maybeList.size(); i++) {
            Assert.assertTrue(testList.get(i).equals(maybeList.get(i)));
        }
    }

    @Test
    public void testGetOrderItemsByReservationIdDoesntExists() {
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE);

        // 2. Ejercitacion
        List<OrderItem> maybeList = reservationDao.getOrderItemsByReservationId(1);

        // 3. PostCondiciones
        Assert.assertTrue(maybeList.isEmpty());
    }
}
