package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.OrderItemStatus;
import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
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
    public Optional<Reservation> getReservationById(int id) {
        return reservationDao.getReservationById(id);
    }

    @Override
    public Reservation create(long restaurantId, long customerId, Timestamp reservationDate) {
        return null;
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
        return reservationDao.create(restaurant.getId(),customer.getCustomerId(),reservationDate);
    }


}
