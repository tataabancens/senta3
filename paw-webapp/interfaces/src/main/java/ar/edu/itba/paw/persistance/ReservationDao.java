package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Reservation;

import java.sql.Timestamp;
import java.util.Optional;

public interface ReservationDao {

    Optional<Reservation> getReservationById(long id);

    Reservation create(long restaurantId, long customerId, Timestamp reservationDate);

}
