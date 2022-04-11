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
    public List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems) {
        return reservationDao.addOrderItemsByReservationId(orderItems);
    }

    @Override
    public List<OrderItem> getOrderItemsByReservationId(long reservationId) {
        return reservationDao.getOrderItemsByReservationId(reservationId);
    }

    @Override
    public List<OrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, int status) {
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
}
