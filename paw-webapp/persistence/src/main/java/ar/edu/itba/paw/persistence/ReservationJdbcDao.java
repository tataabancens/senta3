package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
                    resultSet.getInt("reservationStatus")));

    private static final RowMapper<FullOrderItem> ROW_MAPPER_ORDER_ITEMS = ((resultSet, i) ->
            new FullOrderItem(resultSet.getLong("reservationId"),
                    resultSet.getLong("dishId"),
                    resultSet.getFloat("unitPrice"),
                    resultSet.getInt("quantity"),
                    resultSet.getInt("status"),
                    resultSet.getString("dishname")));

    private static final RowMapper<Restaurant> ROW_MAPPER_RESTAURANT = ((resultSet, i) ->
            new Restaurant(resultSet.getLong("restaurantId"),
                    resultSet.getString("restaurantName"),
                    resultSet.getString("phone"),
                    resultSet.getString("mail"),
                    resultSet.getInt("totalTables"),
                    resultSet.getInt("openHour"),
                    resultSet.getInt("closeHour")));


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
    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
        List<Reservation> query = jdbcTemplate.query("SELECT * FROM reservation WHERE reservationstatus = ?",
                new Object[]{status.ordinal()}, ROW_MAPPER_RESERVATION);
        return query;
    }

    @Override
    public Reservation createReservation(long restaurantId, long customerId, int reservationHour) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurantId", restaurantId);
        reservationData.put("reservationHour", reservationHour);
        reservationData.put("customerId", customerId);
        reservationData.put("reservationstatus", ReservationStatus.ACTIVE.ordinal());

        Number reservationId = jdbcInsertReservation.executeAndReturnKey(reservationData);

        Reservation newReservation = new Reservation(reservationId.longValue(), restaurantId, reservationHour, customerId, ReservationStatus.ACTIVE.ordinal());

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
    public List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        List<FullOrderItem> query = jdbcTemplate.query("SELECT * FROM orderItem NATURAL JOIN dish WHERE status = ?",
                new Object[]{status.ordinal()}, ROW_MAPPER_ORDER_ITEMS);
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
        jdbcTemplate.update("UPDATE reservation SET reservationStatus = ? where reservationId = ?", new Object[]{newStatus.ordinal(), reservationId});
    }

    @Override
    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        jdbcTemplate.update("DELETE from orderitem where status = ?", new Object[]{status.ordinal()});
    }

    @Override
    public List<Integer> getAvailableHours(long restaurantId) {

        List<Reservation> query = jdbcTemplate.query("SELECT * FROM reservation WHERE restaurantId = ?",
                new Object[]{restaurantId}, ROW_MAPPER_RESERVATION);

        List<Restaurant> queryRestaurant = jdbcTemplate.query("SELECT * FROM restaurant WHERE restaurantId = ?",
                new Object[]{restaurantId}, ROW_MAPPER_RESTAURANT);

        Restaurant current = queryRestaurant.get(0);

        int maxTables = current.getTotalTables();

        List<Integer> occupied = new ArrayList<>();
        for (Reservation table:query) {
            occupied.add(table.getReservationHour());
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
        int openHour = current.getOpenHour();
        int closeHour = current.getCloseHour();
        if(openHour < closeHour){
            for(int i=openHour; i<closeHour; i++){
                totalHours.add(i);
            }
        } else if(closeHour < openHour){
            for(int i=openHour; i<24; i++){
                totalHours.add(i);
            }
            for(int i=0; i<closeHour; i++){
                totalHours.add(i);
            }

        } else {

        }

        totalHours.removeAll(notAvailable);

        return totalHours;
    }

    @Override
    public void cancelReservation(long restaurantId, long reservationId){
        jdbcTemplate.update("DELETE FROM reservation WHERE reservationId = ?", new Object[]{reservationId});
    }
}
