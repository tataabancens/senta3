package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.persistance.DishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DishJdbcDao implements DishDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Dish> ROW_MAPPER = ((resultSet, i) ->
            new Dish(resultSet.getLong("dishId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getString("dishName"),
                    resultSet.getInt("price"),
                    resultSet.getString("dishdescription")));

    @Autowired
    public DishJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("dish")
                .usingGeneratedKeyColumns("dishid");
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        List<Dish> query = jdbcTemplate.query("SELECT * FROM dish WHERE dishId = ?",
                new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }


    @Override
    public Dish create(long restaurantId, String dishName, String dishDescription, double price) {
        final Map<String, Object> dishData = new HashMap<>();
        dishData.put("dishName", dishName);
        dishData.put("dishDescription", dishDescription);
        dishData.put("price", (int)price);
        dishData.put("restaurantId", restaurantId);

        Number dishId = jdbcInsert.executeAndReturnKey(dishData);
        return new Dish(dishId.longValue(), restaurantId, dishName, (int)price, dishDescription);
    }
}
