package ar.edu.itba.paw.webapp.dto;

import java.util.ArrayList;
import java.util.List;

public class AvailableHoursDto {
    private List<Integer> availableHours;

    AvailableHoursDto() {

    }

    public static AvailableHoursDto fromHourList(List<Integer> availableHours) {
        AvailableHoursDto toRet = new AvailableHoursDto();
        toRet.availableHours = new ArrayList<>(availableHours);

        return toRet;
    }

    public List<Integer> getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(List<Integer> availableHours) {
        this.availableHours = availableHours;
    }
}
