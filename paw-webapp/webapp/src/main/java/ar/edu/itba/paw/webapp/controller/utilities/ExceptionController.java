package ar.edu.itba.paw.webapp.controller.utilities;

import ar.edu.itba.paw.webapp.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleReservationNotFoundException(){
        return new ModelAndView("error/reservationNotFound");
    }


    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleRestaurantNotFoundException(){
        return new ModelAndView("error/restaurantNotFound");
    }

    @ExceptionHandler(DishNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleDishNotFoundException(){
        return new ModelAndView("error/dishNotFound");
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleCustomerNotFoundException(){
        return new ModelAndView("error/customerNotFound");
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleImageNotFoundException(){
        return new ModelAndView("error/imageNotFound");
    }

/*
    @ExceptionHandler(OrderByException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleOrderByException(){
        return new ModelAndView("error/imageNotFound");
    }
 */

}
