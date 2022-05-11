package ar.edu.itba.par.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.persistance.ReservationDao;
import ar.edu.itba.paw.persistance.RestaurantDao;
import ar.edu.itba.paw.persistance.UserDao;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.ReservationServiceImpl;
import ar.edu.itba.paw.service.RestaurantServiceImpl;
import ar.edu.itba.paw.service.UserServiceImpl;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    private ReservationDao resDao;
    private RestaurantDao restDao;
    private ReservationServiceImpl resService;
    private CustomerService cust;

    @Before
    public void setUp() {
        resDao = Mockito.mock(ReservationDao.class);
        restDao = Mockito.mock(RestaurantDao.class);
        resService = new ReservationServiceImpl(resDao, restDao, cust);
    }
    @Test
    public void testGetTotal(){
        // 1. Setup
        FullOrderItem item1 = new FullOrderItem(1, 1, 1, 20, 1, 1, "plato");
        FullOrderItem item2 = new FullOrderItem(2, 1, 2, 20, 1, 1, "plato2");
        List<FullOrderItem> items = new ArrayList<FullOrderItem>();
        items.add(item1);
        items.add(item2);

        // 2. ejercicio
        Float total = resService.getTotal(items);

        // 3. asserts
        Assert.assertEquals(40, total, 0.001);
    }

    @Test
    public void testUnavailableItems(){
        // 1. Setup
        List<FullOrderItem> items = resDao.getOrderItemsByReservationId(1);
        List<Long> dishIds = new ArrayList<>();

        for (FullOrderItem item:items){
            dishIds.add(item.getDishId());
        }

        List<Long> unavailableDishIds = new ArrayList<>();

        int count;
        for(Long dishId:dishIds){
            count = Collections.frequency(dishIds, dishId);
            if(count > 3 && ! unavailableDishIds.contains(dishId)){
                unavailableDishIds.add(dishId);
            }
        }

        // 2. ejercicio
        List<Long> unavailable = resService.getUnavailableItems(1);

        // 3. asserts
        Assert.assertEquals(unavailable, unavailableDishIds);
    }

}
