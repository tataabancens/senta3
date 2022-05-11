package ar.edu.itba.par.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.RestaurantDao;
import ar.edu.itba.paw.persistance.UserDao;
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

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceImplTest {

    private RestaurantDao resDao;
    private RestaurantServiceImpl resService;

    @Before
    public void setUp() {
        resDao = Mockito.mock(RestaurantDao.class);
        resService = new RestaurantServiceImpl(resDao);
    }
    @Test
    public void testCreateRestaurant() {
        // 1. Setup
        Mockito.when(resDao.create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(new Restaurant(1, "El Pepito", "456789456", "elpepito@gmail.com", 5, 12, 20));

        // 2. ejercicio
        Restaurant res = resService.create("El Pepito", "456789456", "elpepito@gmail.com");


        // 3. asserts
        Assert.assertNotNull(res);
        Assert.assertEquals("El Pepito", res.getRestaurantName());
        Assert.assertEquals("456789456", res.getPhone());
        Assert.assertEquals("elpepito@gmail.com", res.getMail());

    }
}
