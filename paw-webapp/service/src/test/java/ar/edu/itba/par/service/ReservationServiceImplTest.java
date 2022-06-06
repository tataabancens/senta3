package ar.edu.itba.par.service;

import ar.edu.itba.paw.model.*;
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

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
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

    @Before
    public void setUp() {
        resDao = Mockito.mock(ReservationDao.class);
        restDao = Mockito.mock(RestaurantDao.class);
        custDao = Mockito.mock(CustomerDao.class);
        custService = new CustomerServiceImpl(custDao);
        resService = new ReservationServiceImpl(resDao, restDao, custService, null);
    }
    @Test
    public void testGetTotal(){
        // 1. Setup
//        OrderItem item1 = new OrderItem(1, 1, 1, 20, 1, 1, "plato");
//        OrderItem item2 = new OrderItem(2, 1, 2, 20, 1, 1, "plato2");
//        List<OrderItem> items = new ArrayList<OrderItem>();
//        items.add(item1);
//        items.add(item2);
//
//        // 2. ejercicio
//        Float total = resService.getTotal(items);
//
//        // 3. asserts
//        Assert.assertEquals(40, total, 0.001);
    }

    @Test
    public void testUnavailableItems(){
        // 1. Setup
//        List<OrderItem> items = resDao.getOrderItemsByReservationId(1);
//        List<Long> dishIds = new ArrayList<>();
//
//        for (OrderItem item:items){
//            dishIds.add(item.getDish().getId());
//        }
//
//        List<Long> unavailableDishIds = new ArrayList<>();
//
//        int count;
//        for(Long dishId:dishIds){
//            count = Collections.frequency(dishIds, dishId);
//            if(count > 3 && ! unavailableDishIds.contains(dishId)){
//                unavailableDishIds.add(dishId);
//            }
//        }
//
//        // 2. ejercicio
//        List<Long> unavailable = resService.getUnavailableItems(1);
//
//        // 3. asserts
//        Assert.assertEquals(unavailable, unavailableDishIds);
    }

    @Test
    public void testGetAvailableHoursExists() {
        // 1. Setup
        List<Reservation> mockList = new ArrayList<>();
//        for(int i = 1; i < 6; i++) {
//            mockList.add(new Reservation(i, 1, i, 13 + i, 1,
//                    30, "Carlos", "CarlosResto", Timestamp.from(Instant.now())));
//        }
       // Mockito.when(resDao.getAllReservations(1)).thenReturn(mockList);
        Mockito.when(restDao.getRestaurantById(1)).thenReturn(Optional.of(new Restaurant(1, "El Pepito",
                "456789456", "elpepito@gmail.com",
                35, 12, 20)));
        List<Integer> expectedHours = new ArrayList<>();
        expectedHours.add(12);
        expectedHours.add(13);
        expectedHours.add(19);
        expectedHours.removeIf(hour -> hour <= LocalDateTime.now().getHour());


        // 2. ejercicio
        //List<Integer> hours = resService.getAvailableHours(1, 10);

        // 3. asserts
        /*
        Assert.assertNotNull(hours);
        for(int j = 0; j < expectedHours.size(); j++) {
            Assert.assertEquals(expectedHours.get(j), hours.get(j));
        }
         */
    }

    @Test
    public void testGetAvailableHoursEmpty() {
        // 1. Setup
        List<Reservation> mockList = new ArrayList<>();
//        for(int i = 1; i < 6; i++) {
//            mockList.add(new Reservation(i, 1, i, 13 + i, 1,
//                    30, "Carlos", "CarlosResto", Timestamp.from(Instant.now())));
//        }
        //Mockito.when(resDao.getAllReservations(1)).thenReturn(mockList);
        Mockito.when(restDao.getRestaurantById(1)).thenReturn(Optional.of(new Restaurant(1, "El Pepito",
                "456789456", "elpepito@gmail.com",
                35, 14, 18)));
        List<Integer> expectedHours = new ArrayList<>();
        expectedHours.add(12);
        expectedHours.add(13);
        expectedHours.add(19);
        expectedHours.removeIf(hour -> hour <= LocalDateTime.now().getHour());


        // 2. ejercicio
        //List<Integer> hours = resService.getAvailableHours(1, 10);

        // 3. asserts
        //Assert.assertTrue(hours.isEmpty());
    }

    @Test
    public void givenUsingJava8_whenGeneratingRandomAlphanumericString_thenCorrect() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        byte[] seed = "Hola".getBytes(StandardCharsets.UTF_8);
        SecureRandom random = new SecureRandom(seed);

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
    }
}
