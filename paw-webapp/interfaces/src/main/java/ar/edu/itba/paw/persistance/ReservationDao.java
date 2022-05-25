package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {


    Optional<Reservation> getReservationById(long id);

    Optional<Reservation> getReservationByIdAndStatus(long id, List<ReservationStatus> status);

    List<OrderItem> getOrderItemsByStatus(OrderItemStatus status);

//    void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus);
//
//    void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus);
//
//    void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);
//
//    void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId);

    List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page);

    Optional<OrderItem> getOrderItemById(long orderItemId);
}

