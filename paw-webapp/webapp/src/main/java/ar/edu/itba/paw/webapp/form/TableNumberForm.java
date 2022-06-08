package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.customValidator.TableNumberConstraint;

import javax.validation.constraints.Pattern;

public class TableNumberForm {

    @TableNumberConstraint
    @Pattern(regexp = "[1-9][0-9]+")
    String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
