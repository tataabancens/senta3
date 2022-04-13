package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public Optional<Reservation> getReservationByIdAndStatus(long id, ReservationStatus status) {
        return reservationDao.getReservationByIdAndStatus(id, status);
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
        return reservationDao.getOrderItemsByReservationIdAndStatus(reservationId, status);
    }

    @Override
    public Reservation createReservation(Restaurant restaurant, Customer customer, Timestamp reservationDate) {
        return reservationDao.createReservation(restaurant.getId(),customer.getCustomerId(),reservationDate);
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
    public void updateReservationStatus(long reservationId, ReservationStatus newStatus) {
        reservationDao.updateReservationStatus(reservationId, newStatus);
    }

    @Override
    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        reservationDao.deleteOrderItemsByReservationIdAndStatus(reservationId, status);
    }
}
