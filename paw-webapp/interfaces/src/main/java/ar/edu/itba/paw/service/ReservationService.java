package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.OrderItemStatus;
import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(long id);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    List<OrderItem> getOrderItemsByReservationId(long reservationId);

    List<OrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, int status);

    Reservation createReservation(Restaurant restaurant,Customer customer, Timestamp reservationDate);
}
