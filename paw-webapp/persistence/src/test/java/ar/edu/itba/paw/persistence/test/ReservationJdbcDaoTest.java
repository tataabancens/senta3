package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.FullOrderItem;
import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.persistance.ReservationDao;
import ar.edu.itba.paw.persistence.ReservationJdbcDao;
import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.annotation.Rollback;
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
@Rollback
public class ReservationJdbcDaoTest {

    private static final String RESERVATION_TABLE = "reservation";
    private static final String ORDER_ITEM_TABLE = "orderItem";
//    private static final String CUSTOMER_TABLE = "customer";
//    private static final String RESTAURANT_TABLE = "restaurant";
//    private static final String DISH_TABLE = "dish";

    private ReservationJdbcDao reservationDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertReservation;
    private SimpleJdbcInsert jdbcInsertOrderItem;
//    private SimpleJdbcInsert jdbcInsertCustomer;
//    private SimpleJdbcInsert jdbcInsertRestaurant;
//    private SimpleJdbcInsert jdbcInsertDish;

    @Autowired
    private DataSource ds;


    //private ReservationJdbcDao reservationDao = new ReservationJdbcDao(null);

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
        List<FullOrderItem> testList = new ArrayList<>();
        testList.add(new FullOrderItem(1, 1, 1, 100, 1, 1, "Milanesa"));
        testList.add(new FullOrderItem(2, 1, 2, 200, 1, 1, "Empanada"));
/*
        final Map<String, Object> orderItemData = new HashMap<>();
        orderItemData.put("dishid", 3);
        orderItemData.put("reservationid", 1);
        orderItemData.put("unitprice", 890);
        orderItemData.put("quantity", 3);
        orderItemData.put("status", 0);
        final Map<String, Object> orderItemData2 = new HashMap<>();
        orderItemData.put("dishid", 2);
        orderItemData.put("reservationid", 1);
        orderItemData.put("unitprice", 650);
        orderItemData.put("quantity", 3);
        orderItemData.put("status", 0);
        jdbcInsertOrderItem.execute(orderItemData);
        jdbcInsertOrderItem.execute(orderItemData2);
 */

        // 2. Ejercitacion
        List<FullOrderItem> maybeList = reservationDao.getOrderItemsByReservationId(1);

        // 3. PostCondiciones
        Assert.assertFalse(maybeList.isEmpty());
        for (int i = 0; i < maybeList.size(); i++) {
            Assert.assertEquals(testList.get(i), maybeList.get(i));
        }
    }

    @Test
    public void testGetOrderItemsByReservationIdDoesntExists() {
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE);

        // 2. Ejercitacion
        List<FullOrderItem> maybeList = reservationDao.getOrderItemsByReservationId(1);

        // 3. PostCondiciones
        Assert.assertTrue(maybeList.isEmpty());
    }

    @Test
    public void testAddOrderItemsByReservationId() {
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE);
        List<OrderItem> testList = new ArrayList<>();
        testList.add(new OrderItem(1, 1, 100, 3, 0));
        testList.add(new OrderItem(1, 2, 200, 3, 0));

        // 2. Ejercitacion
        reservationDao.addOrderItemsByReservationId(testList);

        // 3. PostCondiciones
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_ITEM_TABLE));
    }

    @Test
    public void testCreateReservation() {
        // 1. Precondiciones
        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESERVATION_TABLE);

        // 2. Ejercitacion
        Reservation maybeReservation = reservationDao.createReservation(1, 1, 1, 1, null);

        // 3. PostCondiciones
        Assert.assertEquals(1, maybeReservation.getReservationId());
    }

}
