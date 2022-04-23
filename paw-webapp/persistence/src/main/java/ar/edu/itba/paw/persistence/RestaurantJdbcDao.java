package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Tables;
import ar.edu.itba.paw.model.User;
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
                    resultSet.getInt("tables")));

    private static final RowMapper<Dish> ROW_MAPPER_DISH = ((resultSet, i) ->
            new Dish(resultSet.getLong("dishId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getString("dishName"),
                    resultSet.getInt("price"),
                    resultSet.getString("dishdescription")));

    private static final RowMapper<Tables> ROW_MAPPER_TABLES = ((resultSet, i) ->
            new Tables(resultSet.getLong("tableId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getInt("hour")));

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
    public List<Integer> getAvailableHours(long restaurantId) {
        /* esto funciona:
        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(1);
        test.add(2);
        test.add(2);
        test.add(3);
        return test;
         */

        /*
        List<Tables> query = jdbcTemplate.query("SELECT * FROM tables WHERE tables.restaurantId = ?",
                new Object[]{restaurantId}, ROW_MAPPER_TABLES);
         */


        //no encuentra la tabla tables o algo así
        List<Tables> query = jdbcTemplate.query("SELECT * FROM tables", ROW_MAPPER_TABLES);

        Restaurant current = getRestaurantById(restaurantId).get();
        int maxTables = current.getTables();

        List<Integer> occupied = new ArrayList<>();
        for (Tables table:query) {
            occupied.add(table.getHour());
        }
        Collections.sort(occupied);

        List<Integer> notAvailable = new ArrayList<>();
        int qty = 1;
        for(int i=0; i<occupied.size()-1; i++){

            if(Objects.equals(occupied.get(i), occupied.get(i + 1))){
                qty++;
                if(qty >= maxTables){
                    notAvailable.add(occupied.get(i));
                }
            } else {
                qty = 1;
            }
        }

        List<Integer> totalHours = new ArrayList<>();
        for(int i=0; i<24; i++){
            totalHours.add(i);
        }

        totalHours.removeAll(notAvailable);

        return totalHours;
    }

    @Override
    public Restaurant create(String restaurantName, String phone, String mail) {
        return null;
    }
}
