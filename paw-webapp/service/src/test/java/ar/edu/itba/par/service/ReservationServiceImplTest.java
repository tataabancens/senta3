package ar.edu.itba.par.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.persistance.CustomerDao;
import ar.edu.itba.paw.persistance.ReservationDao;
import ar.edu.itba.paw.persistance.RestaurantDao;
import ar.edu.itba.paw.service.*;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    private ReservationDao resDao;
    private RestaurantDao restDao;
    private CustomerDao custDao;
    private ReservationServiceImpl resService;

    private CustomerService custService;
    private MailingService mailingService;

    @Before
    public void setUp() {
        resDao = Mockito.mock(ReservationDao.class);
        restDao = Mockito.mock(RestaurantDao.class);
        custDao = Mockito.mock(CustomerDao.class);
        custService = Mockito.mock(CustomerService.class);
        resService = new ReservationServiceImpl(resDao, restDao, custService, null, mailingService);

    }
    @Test
    public void testGetTotal(){
//         1. Setup
        User user = new User("user", "pass", Roles.CUSTOMER.getDescription());
        User userRest = new User("pepito", "pass", Roles.RESTAURANT.getDescription());
        Customer cust1 = new Customer("user", "123", "a@b.com", 0);
        Restaurant rest = new Restaurant("masterchef", "345", "restaurant@g.com", 10, 3, 23, 100);

        DishCategory dishCat = new DishCategory(rest, "cat 1");
        Dish dish1 = new Dish(rest, "dish1", 100, "test", 1, dishCat);
        Dish dish2 = new Dish(rest, "dish2", 200, "test2", 1, dishCat);

        Reservation res1 = new Reservation(rest, cust1, 12, ReservationStatus.OPEN.ordinal(), 1, LocalDateTime.now(), LocalDateTime.now());
        OrderItem item1 = new OrderItem(res1, dish1, dish1.getPrice(), 1, OrderItemStatus.ORDERED);
        OrderItem item2 = new OrderItem(res1, dish2, dish2.getPrice(), 1, OrderItemStatus.ORDERED);

        List<OrderItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);



        // 2. ejercicio
        float total = resService.getTotal(items);

        // 3. asserts
        Assert.assertEquals(300, total, 0.001);
    }

    @Test
    public void testUnavailableItems(){
        // 1. Setup
        User user = new User("user", "pass", Roles.CUSTOMER.getDescription());
        User userRest = new User("pepito", "pass", Roles.RESTAURANT.getDescription());
        Customer cust1 = new Customer("user", "123", "a@b.com", 0);
        Restaurant rest = new Restaurant("masterchef", "345", "restaurant@g.com", 10, 3, 23, 100);

        DishCategory dishCat = new DishCategory(rest, "cat 1");
        Dish dish1 = new Dish(rest, "dish1", 100, "test", 1, dishCat);
        Dish dish2 = new Dish(rest, "dish2", 200, "test2", 1, dishCat);

        Reservation res1 = new Reservation(rest, cust1, 12, ReservationStatus.OPEN.ordinal(), 1, LocalDateTime.now(), LocalDateTime.now());
        OrderItem item1 = new OrderItem(res1, dish1, dish1.getPrice(), 1, OrderItemStatus.SELECTED);
        OrderItem item2 = new OrderItem(res1, dish1, dish1.getPrice(), 1, OrderItemStatus.SELECTED);
        OrderItem item3 = new OrderItem(res1, dish1, dish1.getPrice(), 1, OrderItemStatus.SELECTED);
        OrderItem item4 = new OrderItem(res1, dish1, dish1.getPrice(), 1, OrderItemStatus.SELECTED);
        List<OrderItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        Mockito.when(resDao.getReservationById(res1.getId())).thenReturn(Optional.of(res1));
        Mockito.when(resDao.getOrderItems(res1.getId())).thenReturn(items);

        // 2. ejercicio
        List<Long> unavailable = resService.getUnavailableItems(res1.getId());

        // 3. asserts
        Assert.assertEquals(1, unavailable.size());
        Assert.assertFalse(unavailable.isEmpty());
        Assert.assertEquals(Long.valueOf(dish1.getId()), unavailable.get(0));
    }

    @Test
    public void testGetAvailableHoursExists() {
        // 1. Setup
        Customer cust1 = new Customer("user", "123", "a@b.com", 0);
        Restaurant rest = new Restaurant("masterchef", "345", "restaurant@g.com", 10, 10, 14, 100);

        LocalDateTime tommorrow = LocalDateTime.now().plusDays(1);
        Reservation res1 = new Reservation(rest, cust1, 12, ReservationStatus.OPEN.ordinal(), 10, tommorrow, tommorrow);

        List<Reservation> mockList = new ArrayList<>();
        mockList.add(res1);
        Mockito.when(restDao.getRestaurantById(1)).thenReturn(Optional.of(rest));
        Mockito.when(resDao.getReservationsToCalculateAvailableTables(1, tommorrow)).thenReturn(mockList);


        // 2. ejercicio
        List<Integer> hours = resService.getAvailableHours(1, 1, tommorrow);

        // 3. asserts
        Assert.assertNotNull(hours);
        Assert.assertFalse(hours.contains(12));
    }

    @Test
    public void testGetAvailableHoursEmpty() {
        // 1. Setup
        Customer cust1 = new Customer("user", "123", "a@b.com", 0);
        Restaurant rest = new Restaurant("masterchef", "345", "restaurant@g.com", 10, 10, 14, 100);

        LocalDateTime tommorrow = LocalDateTime.now().plusDays(1);
        Reservation res1 = new Reservation(rest, cust1, 13, ReservationStatus.OPEN.ordinal(), 10, tommorrow, tommorrow);
        Reservation res2 = new Reservation(rest, cust1, 12, ReservationStatus.OPEN.ordinal(), 10, tommorrow, tommorrow);
        Reservation res3 = new Reservation(rest, cust1, 11, ReservationStatus.OPEN.ordinal(), 10, tommorrow, tommorrow);
        Reservation res4 = new Reservation(rest, cust1, 10, ReservationStatus.OPEN.ordinal(), 10, tommorrow, tommorrow);

        List<Reservation> mockList = new ArrayList<>();
        mockList.add(res1);
        mockList.add(res2);
        mockList.add(res3);
        mockList.add(res4);

        Mockito.when(restDao.getRestaurantById(1)).thenReturn(Optional.of(rest));
        Mockito.when(resDao.getReservationsToCalculateAvailableTables(1, tommorrow)).thenReturn(mockList);


        // 2. ejercicio
        List<Integer> hours = resService.getAvailableHours(1, 1, tommorrow);

        // 3. asserts
        Assert.assertTrue(hours.isEmpty());
    }
}
