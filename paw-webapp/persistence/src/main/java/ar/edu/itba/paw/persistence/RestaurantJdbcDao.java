package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class RestaurantJdbcDao implements RestaurantDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Restaurant> ROW_MAPPER_RESTAURANT = ((resultSet, i) ->
            new Restaurant(resultSet.getLong("restaurantId"),
                    resultSet.getString("restaurantName"),
                    resultSet.getString("phone"),
                    resultSet.getString("mail"),
                    resultSet.getInt("totalTables")));

    private static final RowMapper<Dish> ROW_MAPPER_DISH = ((resultSet, i) ->
            new Dish(resultSet.getLong("dishId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getString("dishName"),
                    resultSet.getInt("price"),
                    resultSet.getString("dishdescription"),
                    resultSet.getLong("imageId")));


    @Autowired
    public RestaurantJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Restaurant")
                .usingGeneratedKeyColumns("restaurantId");
    }


    @Override
    public Optional<Restaurant> getRestaurantById(long id) {
        List<Restaurant> query = jdbcTemplate.query("SELECT * FROM restaurant WHERE restaurantId = ?",
                        new Object[]{id}, ROW_MAPPER_RESTAURANT);
        return query.stream().findFirst();
    }

    @Override
    public List<Dish> getRestaurantDishes(long restaurantId) {
        List<Dish> query = jdbcTemplate.query("SELECT * FROM dish WHERE dish.restaurantId = ? ORDER BY dishId",
                new Object[]{restaurantId}, ROW_MAPPER_DISH);
        return query;
    }

    @Override
    public Restaurant create(String restaurantName, String phone, String mail) {
        return null;
    }
}
