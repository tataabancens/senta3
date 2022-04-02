package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.persistance.DishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class DishJdbcDao implements DishDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Dish> ROW_MAPPER = ((resultSet, i) ->
            new Dish(resultSet.getLong("dishId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getString("dishName"),
                    resultSet.getInt("price")));

    @Autowired
    public DishJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS dish ("
                + "dishId SERIAL PRIMARY KEY,"
                + "restaurantId int NOT NULL,"
                + "dishName varchar(100) NOT NULL,"
                + "price int NOT NULL,"
                + "FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId)"
                + ")");
        jdbcTemplate.execute("INSERT INTO dish VALUES (default , 1, 'milanga', 100)");
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        List<Dish> query = jdbcTemplate.query("SELECT * FROM dish WHERE dishId = ?",
                new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Dish create(String dishName, int price) {
        return null;
    }
}
