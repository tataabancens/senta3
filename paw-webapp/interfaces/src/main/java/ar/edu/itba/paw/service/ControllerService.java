package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.User;
import java.util.Optional;

public interface ControllerService {

    Optional<Integer> longParser(Object... str) throws Exception;

    Optional<Integer> orderByParser(String string) throws Exception;

    Optional<Integer> directionParser(String direction);
}
