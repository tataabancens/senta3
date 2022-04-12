package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(long id);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity);

    List<FullOrderItem> getOrderItemsByReservationId(long reservationId);

    List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);

    Reservation createReservation(Restaurant restaurant,Customer customer, Timestamp reservationDate);

    float getTotal(List<FullOrderItem> orderItems);

    void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus);
}
