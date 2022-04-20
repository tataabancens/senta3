package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(long id);

    Optional<Reservation> getReservationByIdAndStatus(long id, ReservationStatus status);

    List<Reservation> getReservationsByStatus(ReservationStatus status);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity);

    List<FullOrderItem> getOrderItemsByReservationId(long reservationId);

    List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);

    List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status);

    Reservation createReservation(Restaurant restaurant,Customer customer, Timestamp reservationDate);

    float getTotal(List<FullOrderItem> orderItems);

    void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus);

    void updateReservationStatus(long reservationId, ReservationStatus newStatus);

    void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);
}
