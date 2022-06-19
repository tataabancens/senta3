package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
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

import static java.time.LocalDateTime.now;

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

    @Test
    @Rollback
    public void testGetOrderItemById(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<OrderItem> orderItem = reservationDao.getOrderItemById(1L);

        // 3. PostCondiciones
        Assert.assertTrue(orderItem.isPresent());
        Assert.assertEquals(1, orderItem.get().getId());

    }

    @Test
    @Rollback
    public void testGetOrderItemById_notExist(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<OrderItem> orderItem = reservationDao.getOrderItemById(99L);

        // 3. PostCondiciones
        Assert.assertFalse(orderItem.isPresent());

    }


    @Test
    @Rollback
    public void testGetOrderItemsByStatus() {
        // 1. Precondiciones

        // 2. Ejercitacion
        List<OrderItem> maybeList = reservationDao.getOrderItemsByStatus(OrderItemStatus.ORDERED);

        // 3. PostCondiciones
        Assert.assertFalse(maybeList.isEmpty());
        Assert.assertEquals(3, maybeList.size());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByStatus_empty() {
        // 1. Precondiciones

        // 2. Ejercitacion
        List<OrderItem> maybeList = reservationDao.getOrderItemsByStatus(OrderItemStatus.DELETED);

        // 3. PostCondiciones
        Assert.assertTrue(maybeList.isEmpty());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByStatusListAndReservation() {
        // 1. Precondiciones
        List<OrderItemStatus>  statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.SELECTED);


        // 2. Ejercitacion
        List<OrderItem> maybeList = reservationDao.getOrderItemsByStatusListAndReservation(2L, statusList);

        // 3. PostCondiciones
        Assert.assertEquals(2, maybeList.size());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByStatusListAndReservation_emptyList() {
        // 1. Precondiciones
        List<OrderItemStatus>  statusList = new ArrayList<>();

        // 2. Ejercitacion
        List<OrderItem> maybeList = reservationDao.getOrderItemsByStatusListAndReservation(2L, statusList);

        // 3. PostCondiciones
        Assert.assertEquals(0, maybeList.size());
    }

    @Test
    @Rollback
    public void testGetOrderItemsByStatusListAndReservation_noReservation() {
        // 1. Precondiciones
        List<OrderItemStatus>  statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.SELECTED);

        // 2. Ejercitacion
        List<OrderItem> maybeList = reservationDao.getOrderItemsByStatusListAndReservation(99L, statusList);

        // 3. PostCondiciones
        Assert.assertEquals(0, maybeList.size());
    }



    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_invalidString1(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "XXXXXX", "ASC", "1", 1);

        // 3. PostCondiciones
        Assert.assertEquals(0, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_invalidString2(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "XXXX", "1", 1);

        // 3. PostCondiciones
        Assert.assertEquals(0, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_invalidString3(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "ASC", "XXXXXX", 1);

        // 3. PostCondiciones
        Assert.assertEquals(0, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetAllReservationsOrderedBy_valid(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<Reservation> allReservations = reservationDao.getAllReservationsOrderedBy(1, "reservationid", "ASC", "9", 1);

        // 3. PostCondiciones
        Assert.assertEquals(5, allReservations.size());
    }

    @Test
    @Rollback
    public void testGetReservationsToCalculateAvailableTables(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<Reservation> reservations = reservationDao.getReservationsToCalculateAvailableTables(1, now());

        // 3. PostCondiciones
        Assert.assertEquals(4, reservations.size());
    }

    @Test
    @Rollback
    public void testGetReservationsToCalculateAvailableTables_tomorrow(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<Reservation> reservations = reservationDao.getReservationsToCalculateAvailableTables(1, now().plusDays(1));

        // 3. PostCondiciones
        Assert.assertEquals(1, reservations.size());
    }

    @Test
    @Rollback
    public void testgetReservationsOfToday(){
        // 1. Precondiciones

        // 2. Ejercitacion
        List<Reservation> reservations = reservationDao.getReservationsOfToday(1);

        // 3. PostCondiciones
        Assert.assertEquals(4, reservations.size());
    }
}
