package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReservationJdbcDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Reservation> ROW_MAPPER = ((resultSet, i) ->
            new Reservation(resultSet.getLong("reservationId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getTimestamp("ReservationDate")));

    @Autowired
    public ReservationJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("reservationId");
    }

    @Override
    public Optional<Reservation> getReservationById(long id) {
        List<Reservation> query = jdbcTemplate.query("SELECT * FROM reservation WHERE reservationId = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }
    @Override
    public Reservation create(long restaurantId, long customerId, Timestamp reservationDate) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurantId", restaurantId);
        reservationData.put("reservationDate", reservationDate);
        reservationData.put("customerId", customerId);

        int reservationId = jdbcInsert.execute(reservationData);
        return new Reservation(reservationId, restaurantId, reservationDate);
    }
}
