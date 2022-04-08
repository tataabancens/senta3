package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.OrderItemStatus;
import ar.edu.itba.paw.persistance.OrderItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

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
        return null;
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
