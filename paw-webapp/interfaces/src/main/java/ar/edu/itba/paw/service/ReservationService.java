package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(long id);

    List<Reservation> getReservationsByStatus(long restaurantId, ReservationStatus status);

    List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems);

    OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity);

    List<FullOrderItem> getOrderItemsByReservationId(long reservationId);

    List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);

    List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status);

    Reservation createReservation(long restaurantId, long customerId, int reservationHour, int qPeople);

    float getTotal(List<FullOrderItem> orderItems);

    void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus);

    void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus);

    void updateReservationStatus(long reservationId, ReservationStatus newStatus);

    void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);

    void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId);

    List<Integer> getAvailableHours(long restaurantId, long qPeople);

    void cancelReservation(long restaurantId, long reservationId);

    List<Long> getUnavailableItems(long reservationId);

    List<FullReservation> getAllReservations(long restaurantId);

    Optional<Reservation> getReservationByIdAndIsActive(long reservationId);

    List<FullOrderItem> getOrderItemsByReservationIdAndOrder(long reservationId);

    List<FullOrderItem> getAllOrderItemsByReservationId(long reservationId);

    List<FullReservation> getReservationsByCustomerId(long customerId);

    void updateReservationById(long reservationId, long customerId, long hour, int getqPeople);

    void checkReservationTime();

    void applyDiscount(long reservationId);

    void cancelDiscount(long reservationId);

    float getDiscountCoefficient(long reservationId);

    boolean canOrderReceipt(Reservation reservation, boolean hasOrdered);

    public List<Reservation> getReservationsSeated(long restaurantId);

    void cleanMaybeReservations(long restaurantId);

    Optional<Reservation> getReservationByIdAndStatus(long reservationId, ReservationStatus maybeReservation);

    List<FullReservation> getReservationsByCustomerIdAndActive(long customerId);

    long getRecommendedDishId(long reservationId);
}
