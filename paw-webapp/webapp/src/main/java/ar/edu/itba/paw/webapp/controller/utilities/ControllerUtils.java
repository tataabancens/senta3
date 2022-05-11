package ar.edu.itba.paw.webapp.controller.utilities;

import java.util.Objects;
import java.util.Optional;

public class ControllerUtils {

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
        if(Objects.equals(filterStatus, "")) {
            return Optional.of(1);
        }
        return ControllerUtils.longParser(filterStatus);
    }

    public static Optional<Integer> orderByParser(String string) {
        if(!Objects.equals(string, "reservationid") && !Objects.equals(string, "customerid") && !Objects.equals(string, "qpeople") && !Objects.equals(string, "reservationhour") && !Objects.equals(string, "reservationstatus")){
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
}