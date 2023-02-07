package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> getReservationBySecurityCode(String securityCode);

    //todo integrate queries in ordercontroller?
    List<OrderItem> getOrderItemsByReservationAndStatus(Reservation reservation, OrderItemStatus status);

    List<OrderItem> getOrderItemsByStatus(OrderItemStatus status);

    void deleteOrderItemsByReservationAndStatus(Reservation reservation, OrderItemStatus status);

    void deleteOrderItemByStatus(OrderItem orderItem, OrderItemStatus status);
    //todo^^

    List<Integer> getAvailableHours(long restaurantId, long qPeople, LocalDateTime reservationDate);


    List<OrderItem> getOrderItemsQuery(String reservationStatus, String orderItemStatus);

    Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople);

    OrderItem createOrderItemPost(String securityCode, long dishId, int qty);

    float getTotal(List<OrderItem> orderItems);

    void updateOrderItemsStatus(Reservation reservation, OrderItemStatus oldStatus, OrderItemStatus newStatus);

    void updateOrderItemStatus(OrderItem orderItem, OrderItemStatus newStatus);

    void updateReservationStatus(Reservation reservation, ReservationStatus newStatus);


    List<Long> getUnavailableItems(long reservationId);

    List<Reservation> getAllReservations(Restaurant restaurant);

    Optional<Reservation> getReservationByIdAndIsActive(String securityCode);

    List<OrderItem> getOrderItemsByReservationAndOrder(Reservation reservation);

    List<OrderItem> getAllOrderItemsByReservation(Reservation reservation);

    //todo check if query
    List<Reservation> getReservationsByCustomerAndActive(Customer customer);

    void updateReservationById(Reservation reservation, Customer customer, long hour, int getqPeople);

    //chron
    void checkReservationTime();

    //chron
    void cleanMaybeReservations();

    void applyDiscount(Reservation reservation);

    void cancelDiscount(Reservation reservation);

    float getDiscountCoefficient(long reservationId);

    boolean canOrderReceipt(Reservation reservation);

    List<Reservation> getReservationsSeated(Restaurant restaurant);

    List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page, long customerId);

    //todo check who used this
    boolean isFromOrder(String isFromOrderP);

    Optional<OrderItem> getOrderItemById(long orderItemId);

    void setReservationSecurityCode(Reservation reservation);

    //todo : check who used this
    boolean isRepeating(Customer customer, Reservation reservation);

    boolean cancelReservation(String securityCode);

//    Reservation createMaybeReservation(Restaurant restaurant, Customer customer, int qPeople);

    void orderReceipt(Reservation reservation);

    void seatCustomer(Reservation reservation, int tableNumber);

    void finishCustomerReservation(Reservation reservation);

    Reservation createReservationPost(long restaurantId, long customerId, int reservationHour, int qPeople, LocalDateTime startedAtTime, LocalDateTime reservationDate);

    boolean patchReservation(String securityCode, String reservationDate, Integer hour, Integer qPeople, Integer table, Boolean hand, Boolean discount, ReservationStatus reservationStatus);

    List<OrderItem> getOrderItemsOfReservation(long id);

    boolean patchOrderItem(String securityCode, long id, String newStatus);
}