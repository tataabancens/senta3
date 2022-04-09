package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class ReservationJdbcDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertReservation;
    private final SimpleJdbcInsert jdbcInsertOrderItem;

    private static final RowMapper<Reservation> ROW_MAPPER_RESERVATION = ((resultSet, i) ->
            new Reservation(resultSet.getLong("reservationId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getTimestamp("ReservationDate")));

    private static final RowMapper<OrderItem> ROW_MAPPER_ORDER_ITEMS = ((resultSet, i) ->
            new OrderItem(resultSet.getLong("reservationId"),
                    resultSet.getLong("dishId"),
                    resultSet.getFloat("unitPrice"),
                    resultSet.getInt("quantity"),
                    resultSet.getInt("status")));



    @Autowired
    public ReservationJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertReservation = new SimpleJdbcInsert(ds)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("reservationId");
        jdbcInsertOrderItem = new SimpleJdbcInsert(ds)
                .withTableName("orderItem");
    }

    @Override
    public Optional<Reservation> getReservationById(long id) {
        List<Reservation> query = jdbcTemplate.query("SELECT * FROM reservation WHERE reservationId = ?",
                                                    new Object[]{id}, ROW_MAPPER_RESERVATION);
        return query.stream().findFirst();
    }
    @Override
    public Reservation create(long restaurantId, long customerId, Timestamp reservationDate) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurantId", restaurantId);
        reservationData.put("reservationDate", reservationDate);
        reservationData.put("customerId", customerId);

        int reservationId = jdbcInsertReservation.execute(reservationData);
        return new Reservation(reservationId, restaurantId, reservationDate);
    }

    @Override
    public List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems) {
        Map<String, ?>[] maps = new Map[orderItems.size()];
        for (int i = 0; i < maps.length; i++) {
            final Map<String, Object> orderItemData = new HashMap<>();
            orderItemData.put("dishId", orderItems.get(i).getDishId());
            orderItemData.put("reservationId", orderItems.get(i).getReservationId());
            orderItemData.put("unitPrice", orderItems.get(i).getUnitPrice());
            orderItemData.put("quantity", orderItems.get(i).getQuantity());
            orderItemData.put("Status", orderItems.get(i).getStatus());

            maps[i] = orderItemData;
        }
        jdbcInsertOrderItem.executeBatch(maps);
        return orderItems;
    }

    @Override
    public List<OrderItem> getOrderItemsByReservationId(long reservationId) {
        List<OrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem WHERE reservationId = ?",
                new Object[]{reservationId}, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }

    @Override
    public List<OrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, int status) {
        List<OrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem WHERE status = ? AND reservationId = ?",
                new Object[]{reservationId}, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }
}
