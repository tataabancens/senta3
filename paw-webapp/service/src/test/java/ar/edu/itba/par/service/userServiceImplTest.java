package ar.edu.itba.par.service;

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
        User user = new User(1, "pepe", "pepe", "ROLE_USER");
        Mockito.when(userDao.create(Mockito.anyString(), Mockito.anyString(), null)).thenReturn(user);

        try {
            User u = userService.create("pepe", "pepe", Roles.CUSTOMER);
        }
        catch (Exception e){
            Assert.fail("unexpected error during create user");
        }

        // no usar verify !! testeamos comportamiento no implementacion!


    }
}
