package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.OrderItemStatus;
import ar.edu.itba.paw.persistance.OrderItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public OrderItemDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("orderItems");
    }

    @Override
    public OrderItem create(long reservationId, long dishId, float unitPrice, int quantity, OrderItemStatus status) {
        return null;
    }

    @Override
    public List<OrderItem> addOrderItemsByReservationId(long reservationId, List<OrderItem> orderItems) {
        List<Map<String, ?>> maps = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            final Map<String, Object> orderItemData = new HashMap<>();
            orderItemData.put("dishId", orderItem.getDishId());
            orderItemData.put("reservationId", orderItem.getReservationId());
            orderItemData.put("unitPrice", orderItem.getUnitPrice());
            orderItemData.put("quantity", orderItem.getQuantity());
            orderItemData.put("Staus", orderItem.getStatus());
            maps.add(orderItemData);
        }

        Map<String, ?>[] toExecute = new Map[maps.size()] ;
        toExecute = maps.toArray(toExecute);
        jdbcInsert.executeBatch(maps.toArray(toExecute));
        return orderItems;
    }

    @Override
    public List<Optional<OrderItem>> getOrderItemsByReservationId(long reservationId) {
        return null;
    }

    @Override
    public List<Optional<OrderItem>> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        return null;
    }
}
