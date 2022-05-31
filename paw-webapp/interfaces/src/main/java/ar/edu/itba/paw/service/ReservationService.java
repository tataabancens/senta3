package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> getReservationBySecurityCode(String securityCode);

    OrderItem createOrderItemByReservation(Reservation reservation, Dish dish, int quantity);

    List<OrderItem> getOrderItemsByReservationAndStatus(Reservation reservation, OrderItemStatus status);

    List<OrderItem> getOrderItemsByStatus(OrderItemStatus status);

    Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople);

    float getTotal(List<OrderItem> orderItems);

    void updateOrderItemsStatus(Reservation reservation, OrderItemStatus oldStatus, OrderItemStatus newStatus);

    void updateOrderItemStatus(OrderItem orderItem, OrderItemStatus newStatus);

    void updateReservationStatus(Reservation reservation, ReservationStatus newStatus);

    void deleteOrderItemsByReservationAndStatus(Reservation reservation, OrderItemStatus status);

    void deleteOrderItemByStatus(OrderItem orderItem, OrderItemStatus status);

    List<Integer> getAvailableHours(long restaurantId, long qPeople, Timestamp reservationDate);

    List<Long> getUnavailableItems(long reservationId);

    List<Reservation> getAllReservations(Restaurant restaurant);

    Optional<Reservation> getReservationByIdAndIsActive(String securityCode);

    List<OrderItem> getOrderItemsByReservationAndOrder(Reservation reservation);

    List<OrderItem> getAllOrderItemsByReservation(Reservation reservation);

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

    Optional<Reservation> getReservationBySecurityCodeAndStatus(String securityCode, ReservationStatus maybeReservation);

    List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page);

    boolean isFromOrder(String isFromOrderP);

    Optional<OrderItem> getOrderItemById(long orderItemId);

    void updateReservationDateById(Reservation reservation, Timestamp reservationDate);

    List<Reservation> getReservationsByCustomerAndStatus(Customer customer, ReservationStatus status);

    void setTableNumber(Reservation reservation, int number);

    void setReservationSecurityCode(Reservation reservation);
}
