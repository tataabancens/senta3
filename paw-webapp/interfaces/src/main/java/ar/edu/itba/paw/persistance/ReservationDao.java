package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.OrderItemStatus;
import ar.edu.itba.paw.model.Reservation;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {


    Optional<Reservation> getReservationById(long id);

    Reservation createReservation(long restaurantId, long customerId, Timestamp reservationDate);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    List<OrderItem> getOrderItemsByReservationId(long reservationId);

    List<OrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, int status);

}
