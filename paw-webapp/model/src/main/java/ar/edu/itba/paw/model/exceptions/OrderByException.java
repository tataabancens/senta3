package ar.edu.itba.paw.model.exceptions;

public class OrderByException extends RuntimeException{
    public OrderByException(String input){
        super("Can not order by " + input);
    }
}
