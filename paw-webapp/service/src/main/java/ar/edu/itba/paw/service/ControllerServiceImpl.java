package ar.edu.itba.paw.service;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ControllerServiceImpl implements ControllerService {

    @Override
    public void longParser(Object... str) throws Exception {
        if(str.length > 0){
            try{
                Long str0 = Long.parseLong((String) str[0]);
            } catch (NumberFormatException e) {
                throw new Exception(str[0] + " is not a number");
            }
        }
        if(str.length > 1){
            try{
                Long str1 = Long.parseLong((String) str[1]);
            } catch (NumberFormatException e) {
                throw new Exception(str[1] + " is not a number");
            }
        }
        if(str.length > 2){
            try{
                Long str2 = Long.parseLong((String) str[2]);
            } catch (NumberFormatException e) {
                throw new Exception(str[2] + " is not a number");
            }
        }
    }

    @Override
    public void orderByParser(String string) throws Exception {
        if(!Objects.equals(string, "reservationid") && !Objects.equals(string, "customerid") && !Objects.equals(string, "qpeople") && !Objects.equals(string, "reservationhour")){
            throw new Exception("cant order by " + string);
        }
    }

}
