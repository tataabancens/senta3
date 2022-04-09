package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public Reservation createReservation(Restaurant restaurant, Customer customer, Timestamp reservationDate) {
        return reservationDao.create(restaurant.getId(),customer.getCustomerId(),reservationDate);
    }

}
