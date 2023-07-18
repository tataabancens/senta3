package ar.edu.itba.par.service;

import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistance.DishCategoryDao;
import ar.edu.itba.paw.persistance.RestaurantDao;
import ar.edu.itba.paw.service.RestaurantServiceImpl;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceImplTest {

    private RestaurantDao restDao;
    private DishCategoryDao dishCategoryDaoDao;
    private RestaurantServiceImpl restService;

    @Before
    public void setUp() {
        restDao = Mockito.mock(RestaurantDao.class);
        dishCategoryDaoDao = Mockito.mock(DishCategoryDao.class);
        restService = new RestaurantServiceImpl(restDao, dishCategoryDaoDao);
    }

    @Test
    public void testCreateRestaurant() {
        // 1. Setup
        Mockito.when(restDao.create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new Restaurant(1, "El Pepito", "456789456", "elpepito@gmail.com",
                        5, 12, 20));

        // 2. ejercicio
        Restaurant res = restService.create("El Pepito", "456789456", "elpepito@gmail.com");

        // 3. asserts
        Assert.assertNotNull(res);
        Assert.assertEquals("El Pepito", res.getRestaurantName());
        Assert.assertEquals("456789456", res.getPhone());
        Assert.assertEquals("elpepito@gmail.com", res.getMail());
    }

    @Test
    public void testUpdateRestaurantHourAndTables() {
        // 1. Setup
        Restaurant rest = new Restaurant("masterchef", "345", "restaurant@g.com", 10, 10, 14, 100);

        // 2. ejercicio
        restService.updateRestaurantHourAndTables(rest, 15, 1, 23);

        // 3. asserts
        Assert.assertEquals(15, rest.getTotalChairs());
        Assert.assertEquals(1, rest.getOpenHour());
        Assert.assertEquals(23, rest.getCloseHour());
    }

    @Test
    public void testCreateDish() {
        // 1. Setup
        Restaurant rest = new Restaurant("masterchef", "345", "restaurant@g.com", 10, 10, 14, 100);
        DishCategory dishCat = new DishCategory(rest, "cat 1");

        // 2. ejercicio
        restService.createDish(rest, "testDish", "testDesc", 100, 1, dishCat);

        // 3. asserts
        Assert.assertEquals(1, rest.getDishes().size());
        Assert.assertEquals("testDish", rest.getDishes().get(0).getDishName());
    }
}
