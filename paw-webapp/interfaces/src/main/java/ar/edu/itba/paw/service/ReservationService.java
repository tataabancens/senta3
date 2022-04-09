package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;

import java.sql.Timestamp;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(int id);
    Reservation createReservation(Restaurant restaurant,Customer customer, Timestamp reservationDate);
}
