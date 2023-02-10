package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    Optional<Reservation> getReservationBySecurityCode(String securityCode);

    Optional<Reservation> getReservationBySecurityCodeAndStatus(String securityCode, List<ReservationStatus> status);

    List<OrderItem> getOrderItemsByStatus(OrderItemStatus status);

//    void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus);
//
//    void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus);
//
//    void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status);
//
//    void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId);

    List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page, long customerId);

    Optional<Reservation> getReservationById(long id);

    Optional<OrderItem> getOrderItemById(long orderItemId);

    List<OrderItem> getOrderItemsByStatusListAndReservation(Long reservationId, List<OrderItemStatus> statusList);

    List<OrderItem> getOrderItems(Long reservationId);

    List<Reservation> getReservationsToCalculateAvailableTables(long restaurantId, LocalDateTime reservationDate);

    List<Reservation> getReservationsOfToday(long restaurantId);

    void deleteCustomer(String securityCode);

    void deleteAllOrderItems(String securityCode);

    List<OrderItem> getOrderItemsByStatusListAndReservationStatusList(List<Integer> reservationStauses, List<Integer> orderItemStatuses);

    int getTotalPages(long restaurantId, String filterStatus, long customerId);
}

