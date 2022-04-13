package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                    resultSet.getTimestamp("ReservationDate"),
                    resultSet.getLong("customerId"),
                    resultSet.getInt("reservationStatus")));

    private static final RowMapper<FullOrderItem> ROW_MAPPER_ORDER_ITEMS = ((resultSet, i) ->
            new FullOrderItem(resultSet.getLong("reservationId"),
                    resultSet.getLong("dishId"),
                    resultSet.getFloat("unitPrice"),
                    resultSet.getInt("quantity"),
                    resultSet.getInt("status"),
                    resultSet.getString("dishname")));



    @Autowired
    public ReservationJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertReservation = new SimpleJdbcInsert(ds)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("reservationid");
        jdbcInsertOrderItem = new SimpleJdbcInsert(ds)
                .withTableName("orderitem")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Reservation> getReservationById(long id) {
        List<Reservation> query = jdbcTemplate.query("SELECT * FROM reservation WHERE reservationId = ?",
                                                    new Object[]{id}, ROW_MAPPER_RESERVATION);
        return query.stream().findFirst();
    }

    @Override
    public Optional<Reservation> getReservationByIdAndStatus(long id, ReservationStatus status) {
        List<Reservation> query = jdbcTemplate.query("SELECT * FROM reservation WHERE reservationId = ? AND reservationstatus = ?",
                new Object[]{id, status.ordinal()}, ROW_MAPPER_RESERVATION);
        return query.stream().findFirst();
    }

    @Override
    public Reservation createReservation(long restaurantId, long customerId, Timestamp reservationDate) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurantId", restaurantId);
        reservationData.put("reservationDate", reservationDate);
        reservationData.put("customerId", customerId);
        reservationData.put("reservationstatus", ReservationStatus.ACTIVE.ordinal());

        Number reservationId = jdbcInsertReservation.executeAndReturnKey(reservationData);

        Reservation newReservation = new Reservation(reservationId.longValue(), restaurantId, reservationDate, customerId, ReservationStatus.ACTIVE.ordinal());

        return newReservation;
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
    public List<FullOrderItem> getOrderItemsByReservationId(long reservationId) {
        List<FullOrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish WHERE reservationId = ?",
                new Object[]{reservationId}, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        List<FullOrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish WHERE status = ? AND reservationId = ?",
                new Object[]{status.ordinal(), reservationId}, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }

    @Override
    public OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity) {
        final Map<String, Object> orderItemData = new HashMap<>();
        orderItemData.put("dishid", dish.getId());
        orderItemData.put("reservationid", reservationId);
        orderItemData.put("unitprice", dish.getPrice());
        orderItemData.put("quantity", quantity);
        orderItemData.put("status", 0);

        Number orderItemId = jdbcInsertOrderItem.executeAndReturnKey(orderItemData);
        return new OrderItem(orderItemId.longValue(), dish.getId(), dish.getPrice(), quantity, OrderItemStatus.SELECTED.ordinal());
    }

    @Override
    public void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus) {
        jdbcTemplate.update("UPDATE orderitem SET status = ? where status = ?", new Object[]{newStatus.ordinal(), oldStatus.ordinal()});
    }

    @Override
    public void updateReservationStatus(long reservationId, ReservationStatus newStatus) {
        jdbcTemplate.update("UPDATE reservation SET reservationStatus = ?", new Object[]{newStatus.ordinal()});
    }

    @Override
    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        jdbcTemplate.update("DELETE from orderitem where status = ?", new Object[]{status.ordinal()});
    }
}
