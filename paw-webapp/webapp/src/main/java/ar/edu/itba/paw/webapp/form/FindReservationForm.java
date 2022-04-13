package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;

public class FindReservationForm {

    @Min(value=1,message = "debe ser un numero positivo")
    private long reservationId;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }
}
