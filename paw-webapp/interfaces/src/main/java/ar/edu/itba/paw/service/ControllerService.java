package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface ControllerService {

    void longParser(Object... str) throws Exception;
}
