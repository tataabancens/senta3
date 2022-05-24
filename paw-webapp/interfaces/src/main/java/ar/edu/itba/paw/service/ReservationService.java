package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(long id);

    OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity);

    List<FullOrderItem> getOrderItemsByReservationId(long reservationId);

    List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);

    List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status);

    Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople);

    float getTotal(List<FullOrderItem> orderItems);

    void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus);

    void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus);

    void updateReservationStatus(Reservation reservation, ReservationStatus newStatus);

    void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);

    void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId);

    List<Integer> getAvailableHours(long restaurantId, long qPeople);

    List<Long> getUnavailableItems(long reservationId);

    List<Reservation> getAllReservations(Restaurant restaurant);

    Optional<Reservation> getReservationByIdAndIsActive(long reservationId);

    List<FullOrderItem> getOrderItemsByReservationIdAndOrder(long reservationId);

    List<FullOrderItem> getAllOrderItemsByReservationId(long reservationId);

    List<Reservation> getReservationsByCustomer(Customer customer);

    List<Reservation> getReservationsByCustomerAndActive(Customer customer);

    void updateReservationById(Reservation reservation, Customer customer, long hour, int getqPeople);

    void checkReservationTime();

    void cleanMaybeReservations();

    void applyDiscount(long reservationId);

    void cancelDiscount(long reservationId);

    float getDiscountCoefficient(long reservationId);

    boolean canOrderReceipt(Reservation reservation, boolean hasOrdered);

    public List<Reservation> getReservationsSeated(Restaurant restaurant);

    Optional<Reservation> getReservationByIdAndStatus(long reservationId, ReservationStatus maybeReservation);

    List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page);

    boolean isFromOrder(String isFromOrderP);
}
