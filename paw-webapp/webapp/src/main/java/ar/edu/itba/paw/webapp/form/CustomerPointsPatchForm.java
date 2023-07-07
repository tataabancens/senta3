package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;

public class CustomerPointsPatchForm {
    @NotNull
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
