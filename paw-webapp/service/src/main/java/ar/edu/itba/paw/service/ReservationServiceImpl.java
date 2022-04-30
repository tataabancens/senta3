package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationDao reservationDao;

    @Autowired
    public ReservationServiceImpl(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Optional<Reservation> getReservationById(long id) {
        return reservationDao.getReservationById(id);
    }

    @Override
    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
        return reservationDao.getReservationsByStatus(status);
    }

    @Override
    public List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems) {
        return reservationDao.addOrderItemsByReservationId(orderItems);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationId(long reservationId) {
        return reservationDao.getOrderItemsByReservationId(reservationId);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(status);
        return reservationDao.getOrderItemsByReservationIdAndStatus(reservationId, statusList);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        return reservationDao.getOrderItemsByStatus(status);
    }

    @Override
    public Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople) {
        return reservationDao.createReservation(restaurant.getId(),customer.getCustomerId(), reservationHour, qPeople);
    }

    @Override
    public OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity) {
        return reservationDao.createOrderItemByReservationId(reservationId, dish, quantity);
    }
    @Override
    public float getTotal(List<FullOrderItem> orderItems) {
        float toRet = 0;
        for (FullOrderItem orderItem : orderItems) {
            toRet += orderItem.getQuantity() * orderItem.getUnitPrice();
        }
        return toRet;
    }

    @Override
    public void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus) {
        reservationDao.updateOrderItemsStatus(reservationId, oldStatus, newStatus);
    }

    @Override
    public void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus) {
        reservationDao.updateOrderItemStatus(orderItemId, newStatus);
    }

    @Override
    public void updateReservationStatus(long reservationId, ReservationStatus newStatus) {
        reservationDao.updateReservationStatus(reservationId, newStatus);
    }

    @Override
    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        reservationDao.deleteOrderItemsByReservationIdAndStatus(reservationId, status);
    }

    @Override
    public List<Integer> getAvailableHours(long restaurantId) {
        return reservationDao.getAvailableHours(restaurantId);
    }

    @Override
    public void cancelReservation(long restaurantId, long reservationId) {
        reservationDao.cancelReservation(restaurantId, reservationId);
    }

    @Override
    public List<Long> getUnavailableItems(long reservationId) {
        return reservationDao.getUnavailableItems(reservationId);
    }

    @Override
    public List<Reservation> getAllReservations(long restaurantId) {
        return reservationDao.getAllReservations(restaurantId);
    }

    @Override
    public Optional<Reservation> getReservationByIdAndIsActive(long reservationId) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.SEATED);

        return reservationDao.getReservationByIdAndStatus(reservationId, statusList);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationIdAndOrder(long reservationId) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.INCOMING);
        statusList.add(OrderItemStatus.DELIVERED);

        return reservationDao.getOrderItemsByReservationIdAndStatus(reservationId, statusList);
    }

    @Override
    public List<FullOrderItem> getAllOrderItemsByReservationId(long reservationId) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.INCOMING);
        statusList.add(OrderItemStatus.DELIVERED);
        statusList.add(OrderItemStatus.FINISHED);

        return reservationDao.getOrderItemsByReservationIdAndStatus(reservationId, statusList);
    }
}
