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
                    resultSet.getInt("totalChairs"),
                    resultSet.getInt("openHour"),
                    resultSet.getInt("closeHour")));


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
    public void updateRestaurantHourAndTables(long restaurantId, int newMaxTables, int newOpenHour, int newCloseHOur) {
        jdbcTemplate.update("UPDATE restaurant SET totalChairs = ?, openHour = ?, closeHour = ? WHERE restaurantId = ?",
            new Object[]{newMaxTables, newOpenHour, newCloseHOur, restaurantId});
    }

    @Override
    public void updateRestaurantName(String name, long restaurantId) {
        jdbcTemplate.update("UPDATE restaurant SET restaurantname = ? WHERE restaurantId = ?", new Object[]{name, restaurantId});
    }

    @Override
    public void updateRestaurantEmail(String mail, long restaurantId) {
        jdbcTemplate.update("UPDATE restaurant SET maul = ? WHERE restaurantId = ?", new Object[]{mail, restaurantId});
    }

    @Override
    public void updatePhone(String phone, long restaurantId) {
        jdbcTemplate.update("UPDATE restaurant SET phone = ? WHERE restaurantId = ?", new Object[]{phone, restaurantId});
    }


    @Override
    public Restaurant create(String restaurantName, String phone, String mail) {
        return null;
    }
}
