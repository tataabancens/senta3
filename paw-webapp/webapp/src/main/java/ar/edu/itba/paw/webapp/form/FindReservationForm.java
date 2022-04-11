package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;

public class FindReservationForm {

    private long reservationId;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }
}
