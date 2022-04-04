package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;

import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> getReservationById(int id);
}
