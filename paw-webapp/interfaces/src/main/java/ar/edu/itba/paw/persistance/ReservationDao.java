package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {


    Optional<Reservation> getReservationById(long id);

    Optional<Reservation> getReservationByIdAndStatus(long id, List<ReservationStatus> status);

    List<Reservation> getReservationsByStatusList(long restaurantId, List<ReservationStatus> statusList);

    Reservation createReservation(long restaurantId, long customerId, int reservationHour, int qPeople, Timestamp startedAtTime);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity);

    List<FullOrderItem> getOrderItemsByReservationId(long reservationId);

    List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, List<OrderItemStatus> status);

    List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status);

    void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus);

    void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus);

    void updateReservationStatus(long reservationId, ReservationStatus newStatus);

    void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);

    void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId);

    void cancelReservation(long restaurantId, long reservationId);

    List<FullReservation> getAllReservations(long restaurantId);

    List<FullReservation> getReservationsByCustomerId(long customerId);

    void updateReservationById(long reservationId, long customerId, long hour, int qPeople);

    void applyDiscount(long reservationId);

    void cancelDiscount(long reservationId);

    List<FullReservation> getReservationsByCustomerIdAndStatus(long customerId, List<ReservationStatus> statusList);

    List<FullOrderItem> getAllOrderItems();

    List<FullReservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus);
}

