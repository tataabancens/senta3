package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.OrderItemStatus;
import ar.edu.itba.paw.model.Reservation;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(int id);

    Reservation create(long restaurantId, long customerId, Timestamp reservationDate);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    List<OrderItem> getOrderItemsByReservationId(long reservationId);

    List<OrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, int status);
}
