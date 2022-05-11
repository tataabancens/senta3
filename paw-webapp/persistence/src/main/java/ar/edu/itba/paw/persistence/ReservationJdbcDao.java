package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
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
                    resultSet.getInt("reservationHour"),
                    resultSet.getLong("customerId"),
                    resultSet.getInt("reservationStatus"),
                    resultSet.getInt("qPeople"),
                    resultSet.getBoolean("reservationDiscount"),
                    resultSet.getTimestamp("startedAtTime")));

    private static final RowMapper<FullReservation> ROW_MAPPER_FULL_RESERVATION = ((resultSet, i) ->
            new FullReservation(resultSet.getLong("reservationId"),
                    resultSet.getLong("restaurantId"),
                    resultSet.getLong("customerId"),
                    resultSet.getInt("reservationHour"),
                    resultSet.getInt("reservationStatus"),
                    resultSet.getInt("qPeople"),
                    resultSet.getString("customerName"),
                    resultSet.getString("restaurantName"),
                    resultSet.getTimestamp("startedAtTime")));

    private static final RowMapper<FullOrderItem> ROW_MAPPER_ORDER_ITEMS = ((resultSet, i) ->
            new FullOrderItem(resultSet.getLong("id"),
                    resultSet.getLong("reservationId"),
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
    public List<FullReservation> getReservationsByCustomerId(long customerId) {
        List<FullReservation> query = jdbcTemplate.query("SELECT * FROM reservation NATURAL JOIN customer cross join restaurant WHERE " +
                                                                "customerId = ? and reservation.restaurantid = restaurant.restaurantid" +
                " ORDER BY reservationId OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY",
                new Object[]{customerId}, ROW_MAPPER_FULL_RESERVATION);
        return query;
    }

    @Override
    public Reservation createReservation(long restaurantId, long customerId, int reservationHour, int qPeople, Timestamp startedAtTime) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurantId", restaurantId);
        reservationData.put("reservationHour", reservationHour);
        reservationData.put("customerId", customerId);
        reservationData.put("reservationstatus", ReservationStatus.OPEN.ordinal());
        reservationData.put("qPeople", qPeople);
        reservationData.put("reservationdiscount", false);
        reservationData.put("startedAtTime", startedAtTime);

        Number reservationId = jdbcInsertReservation.executeAndReturnKey(reservationData);

        Reservation newReservation = new Reservation(reservationId.longValue(), restaurantId, reservationHour, customerId, ReservationStatus.OPEN.ordinal(), qPeople, false, startedAtTime);

        return newReservation;
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationId(long reservationId) {
        List<FullOrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish WHERE reservationId = ?" +
                " ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY",
                new Object[]{reservationId}, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }
    @Override
    public List<Reservation> getReservationsByStatusList(long restaurantId, List<ReservationStatus> statusList) {
        // Building sql query
        if(statusList.isEmpty()){
            return new ArrayList<Reservation>();
        }

        StringBuilder query_string = new StringBuilder("SELECT * FROM reservation WHERE restaurantId = ? AND (");
        Object[] params = new Object[statusList.size() + 1];
        params[0] = restaurantId;
        int i = 1;
        for (ReservationStatus reservationStatus : statusList) {
            if (i != 1) {
                query_string.append("OR ");
            }
            query_string.append("reservationstatus = ? ");
            params[i++] = reservationStatus.ordinal();
        }
        query_string.append(" )");
        query_string.append(" ORDER BY reservationId OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY");
        // Executing
        List<Reservation> query = jdbcTemplate.query(query_string.toString(),
                params, ROW_MAPPER_RESERVATION);
        return query;
    }

    @Override
    public List<FullReservation> getReservationsByCustomerIdAndStatus(long customerId, List<ReservationStatus> statusList) {
        // Building sql query
        if(statusList.isEmpty()){
            return new ArrayList<FullReservation>();
        }

        StringBuilder query_string = new StringBuilder("SELECT * FROM reservation NATURAL JOIN customer cross join restaurant WHERE customerId = ? AND (");
        Object[] params = new Object[statusList.size() + 1];
        params[0] = customerId;
        int i = 1;
        for (ReservationStatus reservationStatus : statusList) {
            if (i != 1) {
                query_string.append("OR ");
            }
            query_string.append("reservationstatus = ? ");
            params[i++] = reservationStatus.ordinal();
        }
        query_string.append(" )");
        // Executing
        List<FullReservation> query = jdbcTemplate.query(query_string.toString(),
                params, ROW_MAPPER_FULL_RESERVATION);
        return query;
    }

    @Override
    public List<FullOrderItem> getAllOrderItems() {
        List<FullOrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish" +
                        " ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY",
                new Object[]{}, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }

    @Override
    public Optional<Reservation> getReservationByIdAndStatus(long id, List<ReservationStatus> statusList) {
        // Building sql query
        if(statusList.isEmpty()){
            return Optional.empty();
        }

        StringBuilder query_string = new StringBuilder("SELECT * FROM reservation WHERE reservationId = ? AND ");
        Object[] params = new Object[statusList.size() + 1];
        params[0] = id;
        int i = 1;
        query_string.append("( ");
        for (ReservationStatus reservationStatus : statusList) {
            if (i != 1) {
                query_string.append("OR ");
            }
            query_string.append("reservationstatus = ? ");
            params[i++] = reservationStatus.ordinal();
        }
        query_string.append(" )");
        query_string.append(" ORDER BY reservationId OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY");
        // Executing
        List<Reservation> query = jdbcTemplate.query(query_string.toString(),
                params, ROW_MAPPER_RESERVATION);
        return query.stream().findFirst();
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, List<OrderItemStatus> statusList) {
        // Building sql query
        if(statusList.isEmpty()){
            return new ArrayList<>();
        }

        StringBuilder query_string = new StringBuilder("SELECT * FROM orderItem NATURAL JOIN dish WHERE reservationId = ? AND ");
        Object[] params = new Object[statusList.size() + 1];
        params[0] = reservationId;
        int i = 1;
        query_string.append("( ");
        for (OrderItemStatus orderItemStatus : statusList) {
            if (i != 1) {
                query_string.append("OR ");
            }
            query_string.append("status = ? ");
            params[i++] = orderItemStatus.ordinal();
        }
        query_string.append(" )");
        query_string.append(" ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY");
        List<FullOrderItem> query = jdbcTemplate.query(query_string.toString(),
                params, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }

    @Override
    public List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        List<FullOrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish WHERE status = ?" +
                        "ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY",
                new Object[]{status.ordinal()}, ROW_MAPPER_ORDER_ITEMS);
        return query;
    }

    @Override
    public OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity) {
        if(!getReservationById(reservationId).isPresent() || dish==null){
            return null;
        }

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
    public void applyDiscount(long reservationId) {
        jdbcTemplate.update("UPDATE reservation SET reservationDiscount = ? where reservationId = ?",
                new Object[]{true, reservationId});
    }

    @Override
    public void cancelDiscount(long reservationId) {
        jdbcTemplate.update("UPDATE reservation SET reservationDiscount = ? where reservationId = ?",
                new Object[]{false, reservationId});
    }

    @Override
    public void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus) {
        jdbcTemplate.update("UPDATE orderitem SET status = ? where status = ? AND reservationId = ?", new Object[]{newStatus.ordinal(), oldStatus.ordinal(), reservationId});
    }

    @Override
    public void updateReservationById(long reservationId, long customerId, long hour, int qPeople) {
        jdbcTemplate.update("UPDATE reservation SET customerId = ?, reservationhour = ?, qpeople = ? where reservationId = ?"
                , new Object[]{customerId, hour, qPeople, reservationId});
    }

    @Override
    public void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus) {
        jdbcTemplate.update("UPDATE orderitem SET status = ? where id = ?", new Object[]{newStatus.ordinal(), orderItemId});
    }

    @Override
    public void updateReservationStatus(long reservationId, ReservationStatus newStatus) {
        jdbcTemplate.update("UPDATE reservation SET reservationStatus = ? where reservationId = ?", new Object[]{newStatus.ordinal(), reservationId});
    }

    @Override
    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        jdbcTemplate.update("DELETE from orderitem where status = ? AND reservationId = ?", new Object[]{status.ordinal(), reservationId});
    }

    @Override
    public void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId) {
        jdbcTemplate.update("DELETE from orderitem where status = ? AND reservationId = ? AND id = ?", new Object[]{status.ordinal(), reservationId, orderItemId});
    }

    @Override
    public List<FullReservation> getAllReservations(long restaurantId) {
        List<FullReservation> query = jdbcTemplate.query("SELECT * FROM reservation NATURAL JOIN customer CROSS JOIN RESTAURANT WHERE restaurant.restaurantId = ? " +
                        "ORDER BY reservationStatus OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY",
                new Object[]{restaurantId}, ROW_MAPPER_FULL_RESERVATION);
        return query;
    }

    @Override
    public List<FullReservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page) {
        String filterStatusString = "";
        if(!Objects.equals(orderBy, "reservationid") && !Objects.equals(orderBy, "customerid") && !Objects.equals(orderBy, "qpeople") && !Objects.equals(orderBy, "reservationhour") && !Objects.equals(orderBy, "reservationstatus")) {
            return new ArrayList<>();
        } else if(!Objects.equals(direction, "ASC") && !Objects.equals(direction, "DESC")) {
            return new ArrayList<>();
        } else if(!filterStatus.matches("[0-9]*")) {
            return new ArrayList<>();
        }

        if(!Objects.equals(filterStatus, "")){
        filterStatusString = " AND reservationStatus = " + filterStatus;
        }

        List<FullReservation> query = jdbcTemplate.query("SELECT * FROM reservation NATURAL JOIN customer CROSS JOIN RESTAURANT WHERE restaurant.restaurantId = ?" + filterStatusString +
                        " ORDER BY " + orderBy + " " + direction + " OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY",
                new Object[]{restaurantId, Math.abs((page-1)*10)}, ROW_MAPPER_FULL_RESERVATION);
        return query;
    }

}
