package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistance.ReservationDao;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationDao reservationDao;
    private RestaurantDao restaurantDao;

    @Autowired
    public ReservationServiceImpl(final ReservationDao reservationDao, final RestaurantDao restaurantDao) {
        this.reservationDao = reservationDao;
        this.restaurantDao = restaurantDao;
    }

    @Override
    public Optional<Reservation> getReservationById(long id) {
        return reservationDao.getReservationById(id);
    }

    @Override
    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
        return reservationDao.getReservationsByStatus(status);
    }

    @Override
    public List<OrderItem> addOrderItemsByReservationId(List<OrderItem> orderItems) {
        return reservationDao.addOrderItemsByReservationId(orderItems);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationId(long reservationId) {
        return reservationDao.getOrderItemsByReservationId(reservationId);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(status);
        return reservationDao.getOrderItemsByReservationIdAndStatus(reservationId, statusList);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        return reservationDao.getOrderItemsByStatus(status);
    }

    @Override
    public Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople) {
        return reservationDao.createReservation(restaurant.getId(),customer.getCustomerId(), reservationHour, qPeople);
    }

    @Override
    public OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity) {
        return reservationDao.createOrderItemByReservationId(reservationId, dish, quantity);
    }
    @Override
    public float getTotal(List<FullOrderItem> orderItems) {
        float toRet = 0;
        for (FullOrderItem orderItem : orderItems) {
            toRet += orderItem.getQuantity() * orderItem.getUnitPrice();
        }
        return toRet;
    }

    @Override
    public void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus) {
        reservationDao.updateOrderItemsStatus(reservationId, oldStatus, newStatus);
    }

    @Override
    public void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus) {
        reservationDao.updateOrderItemStatus(orderItemId, newStatus);
    }

    @Override
    public void updateReservationStatus(long reservationId, ReservationStatus newStatus) {
        reservationDao.updateReservationStatus(reservationId, newStatus);
    }

    @Override
    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
        reservationDao.deleteOrderItemsByReservationIdAndStatus(reservationId, status);
    }

    @Override
    public List<Integer> getAvailableHours(long restaurantId, long qPeople) {
        List<FullReservation> reservations = reservationDao.getAllReservations(restaurantId);
        Restaurant restaurant = restaurantDao.getRestaurantById(1).get();

        int openHour = restaurant.getOpenHour();
        int closeHour = restaurant.getCloseHour();

        List<Integer> totalHours = new ArrayList<>();
        if (openHour < closeHour) {
            for(int i = openHour; i < closeHour; i++) {
                totalHours.add(i);
            }
        } else if( closeHour < openHour ) {
            for (int i = openHour; i<24; i++) {
                totalHours.add(i);
            }
            for (int i = 0; i < closeHour; i++) {
                totalHours.add(i);
            }
        }

        Map<Integer, Integer> map = new HashMap<>();
        for(FullReservation reservation :reservations){
            if(map.containsKey(reservation.getReservationHour())){
                map.put(reservation.getReservationHour(), map.get(reservation.getReservationHour())+reservation.getqPeople());
            } else {
                map.put(reservation.getReservationHour(), reservation.getqPeople());
            }
        }
        List<Integer> notAvailable = new ArrayList<>();
        for(Map.Entry<Integer, Integer> set :map.entrySet()){
            if(set.getValue() + qPeople > restaurant.getTotalChairs()){
                notAvailable.add(set.getKey());
            }
        }
        if(qPeople > restaurant.getTotalChairs()){
            //totalHours = new ArrayList<>();
            return null;
        } else {
            totalHours.removeAll(notAvailable);
        }
        return totalHours;
    }

    @Override
    public void cancelReservation(long restaurantId, long reservationId) {
        reservationDao.cancelReservation(restaurantId, reservationId);
    }

    @Override
    public List<Long> getUnavailableItems(long reservationId) {
        return reservationDao.getUnavailableItems(reservationId);
    }

    @Override
    public List<FullReservation> getAllReservations(long restaurantId) {
        return reservationDao.getAllReservations(restaurantId);
    }

    @Override
    public Optional<Reservation> getReservationByIdAndIsActive(long reservationId) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.SEATED);

        return reservationDao.getReservationByIdAndStatus(reservationId, statusList);
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationIdAndOrder(long reservationId) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.INCOMING);
        statusList.add(OrderItemStatus.DELIVERED);

        return reservationDao.getOrderItemsByReservationIdAndStatus(reservationId, statusList);
    }

    @Override
    public List<FullOrderItem> getAllOrderItemsByReservationId(long reservationId) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.INCOMING);
        statusList.add(OrderItemStatus.DELIVERED);
        statusList.add(OrderItemStatus.FINISHED);

        return reservationDao.getOrderItemsByReservationIdAndStatus(reservationId, statusList);
    }
}
