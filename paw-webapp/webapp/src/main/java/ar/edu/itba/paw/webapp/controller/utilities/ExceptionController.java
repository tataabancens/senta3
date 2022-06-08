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
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleReservationNotFoundException(){
        return new ModelAndView("error/reservationNotFound");
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleRestaurantNotFoundException(){
        return new ModelAndView("error/restaurantNotFound");
    }

    @ExceptionHandler(DishNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleDishNotFoundException(){
        return new ModelAndView("error/dishNotFound");
    }

    @ExceptionHandler(DishCategoryNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleDishCategoryNotFoundException(){
        return new ModelAndView("error/dishCategoryNotFound");
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleCustomerNotFoundException(){
        return new ModelAndView("error/customerNotFound");
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleImageNotFoundException(){
        return new ModelAndView("error/imageNotFound");
    }

    @ExceptionHandler(LongParseException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleLongParseException(){
        return new ModelAndView("error/error400");
    }

    @ExceptionHandler(OrderByException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleorderByParseException(){
        return new ModelAndView("error/error400");
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handleOrderItemNotFoundException(){
        return new ModelAndView("error/orderItemNotFound");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView handleUserNotFoundException(){
        return new ModelAndView("error/userNotFound");
    }



}
