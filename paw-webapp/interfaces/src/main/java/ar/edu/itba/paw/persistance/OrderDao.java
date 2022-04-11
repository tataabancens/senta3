package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Order;

import java.util.Optional;

public interface OrderDao {

    Optional<Order> getOrderById(int id);
}
