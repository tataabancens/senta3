package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.jpa.UserJpaDao;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class UserJpaDaoTest {

    private static final String USERNAME_EXIST = "Juancho";
    private static final String USERNAME_NOT_EXIST = "Juancho el inexistente";

    @Autowired
    private UserJpaDao userDao;

    @Test
    @Rollback
    public void testCreateUser(){
        // 1. Precondiciones

        // 2. Ejercitacion
        User user = userDao.create("USERNAME", "pass", Roles.CUSTOMER);

        // 3. PostCondiciones
        Assert.assertNotNull(user);
        Assert.assertEquals("USERNAME", user.getUsername());
    }

    @Test
    @Rollback
    public void testFindUserById_DoesntExist(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(99L);

        // 3. PostCondiciones
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    @Rollback
    public void testFindUserById_Exists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.getUserById(1);

        // 3. PostCondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(1, maybeUser.get().getId());
    }

    @Test
    @Rollback
    public void testFindUserByName_Exists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.findByName(USERNAME_EXIST);

        // 3. PostCondiciones
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(USERNAME_EXIST, maybeUser.get().getUsername());
    }

    @Test
    @Rollback
    public void testFindUserByName_NotExists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<User> maybeUser = userDao.findByName(USERNAME_NOT_EXIST);

        // 3. PostCondiciones
        Assert.assertFalse(maybeUser.isPresent());
    }
}
