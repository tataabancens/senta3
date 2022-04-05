package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.persistance.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class OrderServiceImpl implements OrderService{

    private OrderDao orderDao;

    @Autowired
    public  OrderServiceImpl(final OrderDao orderDao){
        this.orderDao=orderDao;
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderDao.getOrderById(id);
    }


}
