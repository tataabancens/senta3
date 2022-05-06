package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.enums.DishCategory;
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
                    resultSet.getString("dishdescription"),
                    resultSet.getLong("imageId"),
                    DishCategory.valueOf(resultSet.getString("category"))
                    ));


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
    public Dish create(long restaurantId, String dishName, String dishDescription, double price, long imageId, DishCategory category) {
        final Map<String, Object> dishData = new HashMap<>();
        dishData.put("dishName", dishName);
        dishData.put("dishDescription", dishDescription);
        dishData.put("price", (int)price);
        dishData.put("restaurantId", restaurantId);
        dishData.put("imageId", imageId);
        dishData.put("category", category);

        Number dishId = jdbcInsert.executeAndReturnKey(dishData);
        return new Dish(dishId.longValue(), restaurantId, dishName, (int)price, dishDescription, imageId, category);
    }

    @Override
    public void updateDish(long dishId, String dishName, String dishDescription, double price, DishCategory category, long restaurantId) {
        jdbcTemplate.update("UPDATE dish SET dishname = ?, dishdescription = ?, price = ?, category = ? WHERE dishid = ?", new Object[]{dishName, dishDescription, price, category.getDescription(), dishId});
    }

    @Override
    public void updateDishPhoto(long dishId, long imageId) {
        jdbcTemplate.update("UPDATE dish SET imageId = ? WHERE dishid = ?", new Object[]{imageId, dishId});
    }

    @Override
    public void deleteDish(long dishId) {
        jdbcTemplate.update("DELETE FROM dish WHERE dishId = ?", new Object[]{dishId});
    }
}
