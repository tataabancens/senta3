package ar.edu.itba.par.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.UserDao;
import ar.edu.itba.paw.service.UserServiceImpl;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class userServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl(userDao, null);


    @Test
    public void testCreateUser() {
        // 1. Setup
        Mockito.when(userDao.create("Pepito", "456", Roles.CUSTOMER)).thenReturn(new User(1, "Pepito", "456", Roles.CUSTOMER.getDescription()));

        // 2. ejercicio
        User user = userService.create("Pepito", "456", Roles.CUSTOMER);


        // 3. asserts
        Assert.assertNotNull(user);
        Assert.assertEquals("Pepito", user.getUsername());
        Assert.assertEquals("456", user.getPassword());
        Assert.assertEquals(Roles.CUSTOMER.getDescription(), user.getRole());
    }
}
