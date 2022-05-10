package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
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
import java.util.*;

import static ar.edu.itba.paw.model.enums.DishCategory.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class ReservationJdbcDaoTest {

    private static final String RESERVATION_TABLE = "reservation";
    private static final String ORDER_ITEM_TABLE = "orderItem";
    private static final String CUSTOMER_TABLE = "customer";
    private static final String RESTAURANT_TABLE = "restaurant";
    private static final String DISH_TABLE = "dish";

    private ReservationJdbcDao reservationDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertReservation;
    private SimpleJdbcInsert jdbcInsertOrderItem;
    private SimpleJdbcInsert jdbcInsertCustomer;
    private SimpleJdbcInsert jdbcInsertRestaurant;
    private SimpleJdbcInsert jdbcInsertDish;

    @Autowired
    private DataSource ds;


    //private ReservationJdbcDao reservationDao = new ReservationJdbcDao(null);

    @Before
    public void setUp(){
        reservationDao = new ReservationJdbcDao(ds);
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
    public void testGetReservationById_Exists(){
        // 1. Precondiciones
        //insert dish, create reservation, insert orderItem
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);

        // 2. Ejercitacion
        Optional<Reservation> maybeReservation = reservationDao.getReservationById(reservationId.longValue());

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservation.isPresent());
        Assert.assertEquals(reservationId.longValue(), maybeReservation.get().getReservationId());
    }

    @Test
    @Rollback
    public void testGetReservationById_NotExists(){
        // 1. Precondiciones
        //insert dish, create reservation, insert orderItem
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);

        // 2. Ejercitacion
        Optional<Reservation> maybeReservation = reservationDao.getReservationById(reservationId.longValue()+1);

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservation.isPresent());
    }


    @Test
    @Rollback
    public void testGetReservationsByStatusList_Empty(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);

        // 2. Ejercitacion
        List<Reservation> maybeReservations = reservationDao.getReservationsByStatusList(1, new ArrayList<>());

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservations.isEmpty());
    }

    @Test
    @Rollback
    public void testGetReservationsByStatusList_Full(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId3 = insertReservation(1, 12, 1, ReservationStatus.CHECK_ORDERED.ordinal(), 1);
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.SEATED);
        statusList.add(ReservationStatus.CHECK_ORDERED);

        // 2. Ejercitacion
        List<Reservation> maybeReservations = reservationDao.getReservationsByStatusList(1, statusList);

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservations.isEmpty());
        Assert.assertEquals(2, maybeReservations.size());
    }

    @Test
    @Rollback
    public void testGetReservationsByCustomerIdAndStatus_EmptyList(){
        // 1. Precondiciones
        cleanAllTables();
        Number customerId1 = insertCustomer("customerName", "541124555632", "mail@gmail.com");
        Number customerId2 = insertCustomer("customerName2", "541124555631", "mail2@gmail.com");
        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId3 = insertReservation(1, 12, customerId2.intValue(), ReservationStatus.CHECK_ORDERED.ordinal(), 1);
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.CHECK_ORDERED);

        // 2. Ejercitacion
        List<FullReservation> maybeReservations = reservationDao.getReservationsByCustomerIdAndStatus(customerId1.intValue(), new ArrayList<>());

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservations.isEmpty());
    }

    @Test
    @Rollback
    public void testGetReservationsByCustomerIdAndStatus_noCustomer(){
        // 1. Precondiciones
        cleanAllTables();
        Number customerId1 = insertCustomer("customerName", "541124555632", "mail@gmail.com");
        Number customerId2 = insertCustomer("customerName2", "541124555631", "mail2@gmail.com");
        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId3 = insertReservation(1, 12, customerId2.intValue(), ReservationStatus.CHECK_ORDERED.ordinal(), 1);
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.CHECK_ORDERED);

        // 2. Ejercitacion
        List<FullReservation> maybeReservations = reservationDao.getReservationsByCustomerIdAndStatus(0, statusList);

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservations.isEmpty());
    }

    @Test
    @Rollback
    public void testGetReservationsByCustomerIdAndStatus_Full(){
        // 1. Precondiciones
        cleanAllTables();
        Number customerId1 = insertCustomer("customerName", "541124555632", "mail@gmail.com");
        Number customerId2 = insertCustomer("customerName2", "541124555631", "mail2@gmail.com");
        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId3 = insertReservation(1, 12, customerId2.intValue(), ReservationStatus.CHECK_ORDERED.ordinal(), 1);
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.CHECK_ORDERED);

        // 2. Ejercitacion
        List<FullReservation> maybeReservations = reservationDao.getReservationsByCustomerIdAndStatus(customerId1.intValue(), statusList);

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservations.isEmpty());
        Assert.assertEquals(1, maybeReservations.size());
        Assert.assertEquals(reservationId1.longValue(),maybeReservations.get(0).getReservationId());
    }

    @Test
    @Rollback
    public void testGetReservationsByIdAndStatus_EmptyList(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);

        // 2. Ejercitacion
        Optional<Reservation> maybeReservations = reservationDao.getReservationByIdAndStatus(reservationId1.longValue(), new ArrayList<>());

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservations.isPresent());
    }

    @Test
    @Rollback
    public void testGetReservationsByIdAndStatus_NoReservation(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.SEATED);
        statusList.add(ReservationStatus.CHECK_ORDERED);

        // 2. Ejercitacion
        Optional<Reservation> maybeReservations = reservationDao.getReservationByIdAndStatus(0, statusList);

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservations.isPresent());
    }

    @Test
    @Rollback
    public void testGetReservationsByIdAndStatus_Full(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId3 = insertReservation(1, 12, 1, ReservationStatus.CHECK_ORDERED.ordinal(), 1);
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.CHECK_ORDERED);

        // 2. Ejercitacion
        Optional<Reservation> maybeReservations = reservationDao.getReservationByIdAndStatus(reservationId1.longValue(), statusList);

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservations.isPresent());
        Assert.assertEquals(reservationId1.longValue(), maybeReservations.get().getReservationId());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByReservationIdAndStatus_EmptyList(){
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number orderItemId1 = insertOrderItem(dishId.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        Number orderItemId2 = insertOrderItem(dishId.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
        Number orderItemId3 = insertOrderItem(dishId.intValue(), reservationId2.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());

        // 2. Ejercitacion
        List<FullOrderItem> orderItems = reservationDao.getOrderItemsByReservationIdAndStatus(reservationId1.longValue(), new ArrayList<>());

        // 3. PostCondiciones
        Assert.assertTrue(orderItems.isEmpty());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByReservationIdAndStatus_NoReservation(){
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number orderItemId1 = insertOrderItem(dishId.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        Number orderItemId2 = insertOrderItem(dishId.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
        Number orderItemId3 = insertOrderItem(dishId.intValue(), reservationId2.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);

        // 2. Ejercitacion
        List<FullOrderItem> orderItems = reservationDao.getOrderItemsByReservationIdAndStatus(0, statusList);

        // 3. PostCondiciones
        Assert.assertTrue(orderItems.isEmpty());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByReservationIdAndStatus_Full(){
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number orderItemId1 = insertOrderItem(dishId.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        Number orderItemId2 = insertOrderItem(dishId.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
        Number orderItemId3 = insertOrderItem(dishId.intValue(), reservationId2.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);

        // 2. Ejercitacion
        List<FullOrderItem> orderItems = reservationDao.getOrderItemsByReservationIdAndStatus(reservationId1.longValue(), statusList);

        // 3. PostCondiciones
        Assert.assertFalse(orderItems.isEmpty());
        Assert.assertEquals(1, orderItems.size());
        Assert.assertEquals(orderItemId1.longValue(), orderItems.get(0).getOrderItemId());
    }

    @Test
    @Rollback
    public void testCreateReservation() {
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);

        // 2. Ejercitacion
        Reservation maybeReservation = reservationDao.createReservation(1, 1, 1, 1, null);

        // 3. PostCondiciones
        Assert.assertEquals(reservationId.intValue()+1, maybeReservation.getReservationId());
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, RESERVATION_TABLE));
    }

    @Test
    @Rollback
    public void testCreateOrderItemsByReservationId() {
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        Dish new_dish = new Dish(dishId.longValue(), 1, "Empanada", 100, "sin pasas de uva", 1, MAIN_DISH);

        // 2. Ejercitacion
        OrderItem orderItem = reservationDao.createOrderItemByReservationId(reservationId.longValue(), new_dish, 2);

        // 3. PostCondiciones
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_ITEM_TABLE));
        Assert.assertEquals(new_dish.getId(), orderItem.getDishId());
    }

    @Test
    @Rollback
    public void testCreateOrderItemsByReservationId_NoReservation() {
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Dish new_dish = new Dish(dishId.longValue(), 1, "Empanada", 100, "sin pasas de uva", 1, MAIN_DISH);

        // 2. Ejercitacion
        OrderItem orderItem = reservationDao.createOrderItemByReservationId(0, new_dish, 2);

        // 3. PostCondiciones

        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_ITEM_TABLE));
    }

    @Test
    @Rollback
    public void testCreateOrderItemsByReservationId_NoDish() {
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);

        // 2. Ejercitacion
        OrderItem orderItem = reservationDao.createOrderItemByReservationId(reservationId.longValue(), null, 2);

        // 3. PostCondiciones
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_ITEM_TABLE));
    }


    @Test
    @Rollback
    public void testGetOrderItemsByReservationId_Exists() {
        // 1. Precondiciones
        cleanAllTables();
        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.OPEN.ordinal(), 1);
        Number orderItemId = insertOrderItem(dishId.intValue(), reservationId.intValue(), 100, 1, 0);

        List<FullOrderItem> testList = new ArrayList<>();
        testList.add(new FullOrderItem(orderItemId.longValue(), reservationId.longValue(), dishId.intValue(), 100, 1, 0, "Empanada"));
        //testList.add(new FullOrderItem(2, 1, 2, 200, 1, 1, "Empanada"));

        // 2. Ejercitacion
        List<FullOrderItem> maybeList = reservationDao.getOrderItemsByReservationId(reservationId.longValue());

        // 3. PostCondiciones
        Assert.assertFalse(maybeList.isEmpty());
        for (int i = 0; i < maybeList.size(); i++) {
            Assert.assertEquals(testList.get(i), maybeList.get(i));
        }
    }

    @Test
    @Rollback
    public void testGetOrderItemsByReservationId_DoesntExists() {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitacion
        List<FullOrderItem> maybeList = reservationDao.getOrderItemsByReservationId(1);

        // 3. PostCondiciones
        Assert.assertTrue(maybeList.isEmpty());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByStatus() {
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        Number dishId2 = insertDish("Milanesa", "sin pasas de uva", 200, 1, 1, MAIN_DISH);
        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        insertOrderItem(dishId2.intValue(), reservationId.intValue(), 200, 1, OrderItemStatus.ORDERED.ordinal());
        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());

        // 2. Ejercitacion
        List<FullOrderItem> maybeList = reservationDao.getOrderItemsByStatus(OrderItemStatus.ORDERED);

        // 3. PostCondiciones
        Assert.assertFalse(maybeList.isEmpty());
        Assert.assertEquals(2, maybeList.size());
    }

    @Test
    @Rollback
    public void testApplyDiscount(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);

        // 2. Ejercitacion
        reservationDao.applyDiscount(reservationId.longValue());

        // 3. PostCondiciones
        //no explotó

    }

    @Test
    @Rollback
    public void testApplyDiscount_invalidReservation(){
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitacion
        reservationDao.applyDiscount(0);

        // 3. PostCondiciones
        //no explotó
    }

    @Test
    @Rollback
    public void testGetAllOrderItems(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
        insertOrderItem(dishId1.intValue(), reservationId2.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());


        // 2. Ejercitacion
        List<FullOrderItem> allOrderItems = reservationDao.getAllOrderItems();

        // 3. PostCondiciones
        Assert.assertEquals(3, allOrderItems.size());
    }

    @Test
    @Rollback
    public void testDeleteOrderItemsByReservationIdAndStatus(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());


        // 2. Ejercitacion
        reservationDao.deleteOrderItemsByReservationIdAndStatus(reservationId.longValue(), OrderItemStatus.ORDERED);

        // 3. PostCondiciones
        Assert.assertEquals(1, reservationDao.getAllOrderItems().size());
    }

    @Test
    @Rollback
    public void testDeleteOrderItemsByReservationIdAndStatus_invalid(){
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitacion
        reservationDao.deleteOrderItemsByReservationIdAndStatus(0, OrderItemStatus.ORDERED);

        // 3. PostCondiciones
        //no explotó
    }

    @Test
    @Rollback
    public void testGetAllReservations(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);

        // 2. Ejercitacion
        List<FullReservation> allReservations = reservationDao.getAllReservations(1);

        // 3. PostCondiciones
        Assert.assertEquals(2, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_invalidString1(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);

        // 2. Ejercitacion
        List<FullReservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "XXXXXX", "ASC", "1", 1);

        // 3. PostCondiciones
        Assert.assertEquals(0, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_invalidString2(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);

        // 2. Ejercitacion
        List<FullReservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "XXXX", "1", 1);

        // 3. PostCondiciones
        Assert.assertEquals(0, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_invalidString3(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);

        // 2. Ejercitacion
        List<FullReservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "ASC", "XXXXXX", 1);

        // 3. PostCondiciones
        Assert.assertEquals(0, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_valid(){
        // 1. Precondiciones
        cleanAllTables();
        Number reservationId1 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);
        Number reservationId2 = insertReservation(1, 12, 1, ReservationStatus.SEATED.ordinal(), 1);

        // 2. Ejercitacion
        List<FullReservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "ASC", "1", 1);

        // 3. PostCondiciones
        Assert.assertEquals(2, allReservations.size());
    }



}
