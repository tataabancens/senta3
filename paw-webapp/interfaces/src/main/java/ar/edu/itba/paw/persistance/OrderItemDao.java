package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.OrderItemStatus;

import java.util.List;
import java.util.Optional;

public interface OrderItemDao {
    OrderItem create(long reservationId, long dishId, float unitPrice,
                     int quantity, OrderItemStatus status);

    List<OrderItem> addOrderItemsByReservationId(long reservationId, List<OrderItem> orderItems);

    List<Optional<OrderItem>> getOrderItemsByReservationId(long reservationId);

    List<Optional<OrderItem>> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);
}
