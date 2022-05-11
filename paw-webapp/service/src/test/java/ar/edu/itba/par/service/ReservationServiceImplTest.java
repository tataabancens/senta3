package ar.edu.itba.par.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
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
        Mockito.when(resDao.createReservation(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn(new Reservation(1, 1, 22, 1, 2, 12, false, Timestamp.from(Instant.now())));

        // 2. ejercicio
        Reservation res = resService.createReservation(1, 1, 22, 1);


        // 3. asserts
        
    }

}
