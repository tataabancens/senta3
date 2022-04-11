package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(long id);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity);

    List<OrderItem> getOrderItemsByReservationId(long reservationId);

    List<OrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, int status);

    Reservation createReservation(Restaurant restaurant,Customer customer, Timestamp reservationDate);
}
