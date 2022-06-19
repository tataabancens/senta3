package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistence.jpa.ReservationJpaDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.paw.persistence.jpa.UserJpaDao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class ReservationJpaDaoTest {

    private static final long USERID_EXISTS = 1;
    private static final long USERID_NOT_EXISTS = 9999;

    private static final long CUSTOMERID_EXISTS = 1;

    private static final String USERNAME_EXIST = "Juancho";
    private static final String USERNAME_NOT_EXIST = "Juancho el inexistente";


    @Autowired
    private ReservationJpaDao reservationDao;

    @Autowired
    private UserJpaDao userDao;

    @PersistenceContext
    private EntityManager em;


    @Rollback
    @Test
    public void testGetReservationById_Exists(){
        // 1. Precondiciones
        //insert dish, create reservation, insert orderItem

        // 2. Ejercitacion
        Optional<Reservation> maybeReservation = reservationDao.getReservationById(1);

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservation.isPresent());
        Assert.assertEquals(1, maybeReservation.get().getId());
    }

    @Rollback
    @Test
    public void testGetReservationBySecurityCode_Exists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Reservation> maybeReservation = reservationDao.getReservationBySecurityCode("AAAAAA");

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservation.isPresent());
        Assert.assertEquals(1, maybeReservation.get().getId());
        Assert.assertEquals("AAAAAA", maybeReservation.get().getSecurityCode());
    }


    @Test
    @Rollback
    public void testGetReservationById_NotExists(){
        // 1. Precondiciones


        // 2. Ejercitacion
        Optional<Reservation> maybeReservation = reservationDao.getReservationById(999);

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservation.isPresent());
    }

    @Test
    @Rollback
    public void testGetReservationBySecurityCode_NotExists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Reservation> maybeReservation = reservationDao.getReservationBySecurityCode("z");

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservation.isPresent());
    }


    @Test
    @Rollback
    public void testGetReservationsByCustomerIdAndStatus_EmptyList(){
        // 1. Precondiciones
        List<ReservationStatus> statusList = new ArrayList<>();

        // 2. Ejercitacion
        Optional<Reservation> maybeReservations = reservationDao.getReservationBySecurityCodeAndStatus("AAAAAA", statusList);

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservations.isPresent());
    }

    @Test
    @Rollback
    public void testGetReservationsByCustomerIdAndStatus_noCustomer(){
        // 1. Precondiciones
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.CHECK_ORDERED);

        // 2. Ejercitacion
        Optional<Reservation> maybeReservations = reservationDao.getReservationBySecurityCodeAndStatus("z", statusList);

        // 3. PostCondiciones
        Assert.assertFalse(maybeReservations.isPresent());
    }

    @Test
    @Rollback
    public void testGetReservationsByCustomerIdAndStatus_Full(){
        // 1. Precondiciones
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.SEATED);

        // 2. Ejercitacion
        Optional<Reservation> maybeReservations = reservationDao.getReservationBySecurityCodeAndStatus("AAAAAA", statusList);

        // 3. PostCondiciones
        Assert.assertTrue(maybeReservations.isPresent());
        Assert.assertEquals("AAAAAA",maybeReservations.get().getSecurityCode());
        Assert.assertEquals(ReservationStatus.SEATED,maybeReservations.get().getReservationStatus());
    }


    @Test
    @Rollback
    public void testGetOrderItems(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<OrderItem> orderItems = reservationDao.getOrderItems(1L);

        // 3. PostCondiciones
        Assert.assertFalse(orderItems.isEmpty());
        Assert.assertEquals(2, orderItems.size());
    }

    @Test
    @Rollback
    public void testGetOrderItems_noReservation(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<OrderItem> orderItems = reservationDao.getOrderItems(99L);

        // 3. PostCondiciones
        Assert.assertTrue(orderItems.isEmpty());
    }

    @Test
    @Rollback
    public void testGetOrderItems_noOrderItems(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<OrderItem> orderItems = reservationDao.getOrderItems(4L);

        // 3. PostCondiciones
        Assert.assertTrue(orderItems.isEmpty());

    }


    
////
////
////    @Test
////    @Rollback
////    public void testGetOrderItemsByReservationId_Exists() {
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number dishId = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        Number reservationId = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.OPEN.ordinal(), 1);
////        Number orderItemId = insertOrderItem(dishId.intValue(), reservationId.intValue(), 100, 1, 0);
////
////        List<OrderItem> testList = new ArrayList<>();
////        testList.add(new OrderItem(orderItemId.longValue(), reservationId.longValue(), dishId.intValue(), 100, 1, 0, "Empanada"));
////        //testList.add(new FullOrderItem(2, 1, 2, 200, 1, 1, "Empanada"));
////
////        // 2. Ejercitacion
////        List<OrderItem> maybeList = reservationDao.getOrderItemsByReservationId(reservationId.longValue());
////
////        // 3. PostCondiciones
////        Assert.assertFalse(maybeList.isEmpty());
////        for (int i = 0; i < maybeList.size(); i++) {
////            Assert.assertEquals(testList.get(i), maybeList.get(i));
////        }
////    }
////
////    @Test
////    @Rollback
////    public void testGetOrderItemsByReservationId_DoesntExists() {
////        // 1. Precondiciones
////        cleanAllTables();
////
////        // 2. Ejercitacion
////        List<OrderItem> maybeList = reservationDao.getOrderItemsByReservationId(1);
////
////        // 3. PostCondiciones
////        Assert.assertTrue(maybeList.isEmpty());
////    }
////
////    @Test
////    @Rollback
////    public void testGetOrderItemsByStatus() {
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        Number dishId2 = insertDish("Milanesa", "sin pasas de uva", 200, 1, 1, MAIN_DISH);
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
////        insertOrderItem(dishId2.intValue(), reservationId.intValue(), 200, 1, OrderItemStatus.ORDERED.ordinal());
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
////
////        // 2. Ejercitacion
////        List<OrderItem> maybeList = reservationDao.getOrderItemsByStatus(OrderItemStatus.ORDERED);
////
////        // 3. PostCondiciones
////        Assert.assertFalse(maybeList.isEmpty());
////        Assert.assertEquals(2, maybeList.size());
////    }
////
////    @Test
////    @Rollback
////    public void testApplyDiscount(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////
////        // 2. Ejercitacion
//////        reservationDao.applyDiscount(reservationId.longValue());
////
////        // 3. PostCondiciones
//////        Optional<Reservation> reservation = jdbcTemplate.query("SELECT * FROM reservation WHERE reservationId = ?",
//////                new Object[]{reservationId.longValue()}, ROW_MAPPER_RESERVATION).stream().findFirst();
//////        Assert.assertTrue(reservation.isPresent());
//////        Assert.assertTrue(reservation.get().isReservationDiscount());
////
////    }
////
////    @Test
////    @Rollback
////    public void testApplyDiscount_invalidReservation(){
////        // 1. Precondiciones
////        cleanAllTables();
////
////        // 2. Ejercitacion
//////        reservationDao.applyDiscount(0);
////
////        // 3. PostCondiciones
////        //no explotó
////    }
////
////    @Test
////    @Rollback
////    public void testGetAllOrderItems(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
////        insertOrderItem(dishId1.intValue(), reservationId2.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
////
////
////        // 2. Ejercitacion
////        // List<FullOrderItem> allOrderItems = reservationDao.getAllOrderItems();
////
////        // 3. PostCondiciones
////        // Assert.assertEquals(3, allOrderItems.size());
////    }
////
////    @Test
////    @Rollback
////    public void testDeleteOrderItemsByReservationIdAndStatus(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
////
////
////        // 2. Ejercitacion
//////        reservationDao.deleteOrderItemsByReservationIdAndStatus(reservationId.longValue(), OrderItemStatus.ORDERED);
////
////        // 3. PostCondiciones
////        List<OrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY",
////                new Object[]{}, ROW_MAPPER_ORDER_ITEMS);
////        Assert.assertEquals(1, query.size());
////
////    }
////
////    @Test
////    @Rollback
////    public void testDeleteOrderItemsByReservationIdAndStatus_invalid(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
////        insertOrderItem(dishId1.intValue(), reservationId.intValue(), 100, 1, OrderItemStatus.DELIVERED.ordinal());
////
////        // 2. Ejercitacion
//////        reservationDao.deleteOrderItemsByReservationIdAndStatus(0, OrderItemStatus.ORDERED);
////
////        // 3. PostCondiciones
////        List<OrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY",
////                new Object[]{}, ROW_MAPPER_ORDER_ITEMS);
////        Assert.assertEquals(2, query.size());
////    }
////
////    @Test
////    @Rollback
////    public void testGetAllReservations(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////
////        // 2. Ejercitacion
////        //List<Reservation> allReservations = reservationDao.getAllReservations(1);
////
////        // 3. PostCondiciones
////        //Assert.assertEquals(2, allReservations.size());
////    }
////
////    @Test
////    @Rollback
////    public void testGetAllReservationsOrderedBy_invalidString1(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////
////        // 2. Ejercitacion
////        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "XXXXXX", "ASC", "1", 1);
////
////        // 3. PostCondiciones
////        Assert.assertEquals(0, allReservations.size());
////    }
////
////    @Test
////    @Rollback
////    public void testGetAllReservationsOrderedBy_invalidString2(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////
////        // 2. Ejercitacion
////        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "XXXX", "1", 1);
////
////        // 3. PostCondiciones
////        Assert.assertEquals(0, allReservations.size());
////    }
////
////    @Test
////    @Rollback
////    public void testGetAllReservationsOrderedBy_invalidString3(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////
////        // 2. Ejercitacion
////        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "ASC", "XXXXXX", 1);
////
////        // 3. PostCondiciones
////        Assert.assertEquals(0, allReservations.size());
////    }
////
////    @Test
////    @Rollback
////    public void testGetAllReservationsOrderedBy_valid(){
////        // 1. Precondiciones
////        cleanAllTables();
////        Number customerId1 = insertCustomer("Jake el perro", "54112457896", "jake@gmail.com");
////        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////        Number reservationId2 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.SEATED.ordinal(), 1);
////
////        // 2. Ejercitacion
////        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "ASC", "1", 1);
////
////        // 3. PostCondiciones
////        Assert.assertEquals(2, allReservations.size());
////    }
}
