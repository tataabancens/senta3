package ar.edu.itba.paw.webapp.controller.utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.Date;

public enum ControllerUtils {;

    public static Optional<Integer> longParser(Object... str) {
        if(str.length > 0){
            try{
                Long str0 = Long.parseLong((String) str[0]);
            } catch (NumberFormatException e) {
                //throw new Exception(str[0] + " is not a number");
                return Optional.empty();
            }
        }
        if(str.length > 1){
            try{
                Long str1 = Long.parseLong((String) str[1]);
            } catch (NumberFormatException e) {
                //throw new Exception(str[1] + " is not a number");
                return Optional.empty();
            }
        }
        if(str.length > 2){
            try{
                Long str2 = Long.parseLong((String) str[2]);
            } catch (NumberFormatException e) {
                //throw new Exception(str[2] + " is not a number");
                return Optional.empty();
            }
        }
        return Optional.of(1);
    }

    public static Optional<Integer> filterStatusParser(String filterStatus) {
        if(Objects.equals(filterStatus, "9")) {
            return Optional.of(1);
        }
        return ControllerUtils.longParser(filterStatus);
    }

    public static Optional<Integer> orderByParser(String string) {
        if(!Objects.equals(string, "reservationid") && !Objects.equals(string, "customerid") && !Objects.equals(string, "qpeople") && !Objects.equals(string, "reservationhour") && !Objects.equals(string, "reservationdate") && !Objects.equals(string, "reservationstatus") && !Objects.equals(string, "tablenumber")){
            return Optional.empty();
        }
        return Optional.of(1);
    }

    public static Optional<Integer> directionParser(String direction) {
        if(!Objects.equals(direction, "ASC") && !Objects.equals(direction, "DESC")){
            return Optional.empty();
        }
        return Optional.of(1);
    }

    public static Optional<Timestamp> timestampParser(String strTime){
        try{
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(strTime + " 23:59:59.99");
            return Optional.of(timestamp);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
