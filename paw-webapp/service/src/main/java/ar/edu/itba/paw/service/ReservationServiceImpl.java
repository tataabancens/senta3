package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistance.ReservationDao;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationDao reservationDao;
    private final RestaurantDao restaurantDao;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private static final int POINTS_TO_DISCOUNT = 100;

    @Autowired
    public ReservationServiceImpl(final ReservationDao reservationDao, final RestaurantDao restaurantDao,
                                    final CustomerService customerService, final RestaurantService restaurantService) {
        this.reservationDao = reservationDao;
        this.restaurantDao = restaurantDao;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
    }

    @Transactional
    @Override
    public Optional<Reservation> getReservationById(long id) {
        return reservationDao.getReservationById(id);
    }

    @Transactional
    @Override
    public List<Reservation> getReservationsSeated(Restaurant restaurant) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.CHECK_ORDERED);
        statusList.add(ReservationStatus.SEATED);

        return restaurant.getReservationsByStatusList(statusList);
    }

    @Transactional
    @Override
    public List<Reservation> getAllReservations(Restaurant restaurant) {
        return restaurant.getReservations();
    }

    @Transactional
    @Override
    public List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page) {
        return reservationDao.getAllReservationsOrderedBy(restaurantId, orderBy, direction, filterStatus, page);
    }

    @Transactional
    @Override
    public List<OrderItem> getOrderItemsByReservationAndStatus(Reservation reservation, OrderItemStatus status) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(status);
        return reservationDao.getOrderItemsByStatusListAndReservation(reservation.getId(), statusList);
    }

    @Transactional
    @Override
    public List<OrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        return reservationDao.getOrderItemsByStatus(status);
    }

    @Transactional
    @Override
    public Optional<OrderItem> getOrderItemById(long orderItemId) {
        return reservationDao.getOrderItemById(orderItemId);
    }

    @Transactional
    @Override
    public Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople) {
        return customer.createReservation(restaurant, customer, reservationHour, qPeople, new Timestamp(System.currentTimeMillis()));
    }

    @Transactional
    @Override
    public OrderItem createOrderItemByReservation(Reservation reservation, Dish dish, int quantity) {
        return reservation.createOrderItem(dish, quantity);
    }


    @Override
    public float getTotal(List<OrderItem> orderItems) {
        float toRet = 0;
        for (OrderItem orderItem : orderItems) {
            toRet += orderItem.getQuantity() * orderItem.getUnitPrice();
        }
        return toRet;
    }


    @Transactional
    @Override
    public List<Integer> getAvailableHours(long restaurantId, long qPeople) {
        Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId).get();
        List<Reservation> reservations = restaurant.getReservations();

        List<ReservationStatus> ignoreStatus = new ArrayList<>();
        ignoreStatus.add(ReservationStatus.MAYBE_RESERVATION);
        ignoreStatus.add(ReservationStatus.CANCELED);
        ignoreStatus.add(ReservationStatus.FINISHED);

        reservations.removeIf(res -> ignoreStatus.contains(res.getReservationStatus()));


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
        for(Reservation reservation :reservations){
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
            return new ArrayList<>();
        } else {
            totalHours.removeAll(notAvailable);
        }
        totalHours.removeIf(hour -> hour <= LocalDateTime.now().getHour());
        return totalHours;
    }

    @Transactional
    @Override
    public List<Long> getUnavailableItems(long reservationId) {
        Reservation reservation = getReservationById(reservationId).get();
        List<OrderItem> query = reservationDao.getOrderItems(reservation);

        List<Long> dishIds = new ArrayList<>();

        for (OrderItem item:query){
            dishIds.add(item.getDish().getId());
        }

        List<Long> unavailableDishIds = new ArrayList<>();

        int count;
        for(Long dishId:dishIds){
            count = Collections.frequency(dishIds, dishId);
            if(count > 3 && ! unavailableDishIds.contains(dishId)){
                unavailableDishIds.add(dishId);
            }
        }
        return unavailableDishIds;
    }

    @Transactional
    @Override
    public Optional<Reservation> getReservationByIdAndIsActive(long reservationId) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.SEATED);

        return reservationDao.getReservationByIdAndStatus(reservationId, statusList);
    }

    @Transactional
    @Override
    public Optional<Reservation> getReservationByIdAndStatus(long reservationId, ReservationStatus status) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(status);

        return reservationDao.getReservationByIdAndStatus(reservationId, statusList);
    }

    @Transactional
    @Override
    public List<Reservation> getReservationsByCustomerAndActive(Customer customer) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.SEATED);
        return customer.getReservationsByStatusList(statusList);
    }

    @Transactional
    @Override
    public List<Reservation> getReservationsByCustomer(Customer customer) {
        return customer.getReservations();
    }

    @Transactional
    @Override
    public List<OrderItem> getOrderItemsByReservationAndOrder(Reservation reservation) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.INCOMING);
        statusList.add(OrderItemStatus.DELIVERED);

        return reservationDao.getOrderItemsByStatusListAndReservation(reservation.getId(), statusList);
    }

    @Transactional
    @Override
    public List<OrderItem> getAllOrderItemsByReservation(Reservation reservation) {
        List<OrderItemStatus> statusList = new ArrayList<>();
        statusList.add(OrderItemStatus.ORDERED);
        statusList.add(OrderItemStatus.INCOMING);
        statusList.add(OrderItemStatus.DELIVERED);
        statusList.add(OrderItemStatus.FINISHED);

        return reservationDao.getOrderItemsByStatusListAndReservation(reservation.getId(), statusList);
    }

    @Transactional
    @Override
    public void updateOrderItemsStatus(Reservation reservation, OrderItemStatus oldStatus, OrderItemStatus newStatus) {
        reservationDao.getOrderItems(reservation).forEach(o -> {
            if (o.getStatus().ordinal() == oldStatus.ordinal())
                o.setStatus(newStatus);
        });
    }

    @Transactional
    @Override
    public void updateOrderItemStatus(OrderItem orderItem, OrderItemStatus newStatus) {
        orderItem.setStatus(newStatus);
    }

    @Transactional
    @Override
    public void deleteOrderItemsByReservationAndStatus(Reservation reservation, OrderItemStatus status) {
        reservationDao.getOrderItems(reservation).forEach(o -> {
            if(o.getStatus().ordinal() == status.ordinal())
                o.setStatus(OrderItemStatus.DELETED);
        });
    }

    @Transactional
    @Override
    public void deleteOrderItemByStatus(OrderItem orderItem, OrderItemStatus status) {
        if(orderItem.getStatus() == status)
            orderItem.setStatus(OrderItemStatus.DELETED);
    }

    @Transactional
    @Override
    public void updateReservationStatus(Reservation reservation, ReservationStatus newStatus) {
        reservation.setReservationStatus(newStatus);
    }

    @Transactional
    @Override
    public void updateReservationById(Reservation reservation, Customer customer, long hour, int qPeople) {
        reservation.setReservationHour((int) hour);
        reservation.setCustomer(customer);
        reservation.setqPeople(qPeople);
    }

    @Scheduled(cron = "0 1/31 * * * ?")
    @Transactional
    @Override
    public void checkReservationTime() {
        LocalDateTime now = LocalDateTime.now();
        Restaurant restaurant = restaurantService.getRestaurantById(1).get();

        List<Reservation> allReservations = restaurant.getReservations();
        for(Reservation reservation :allReservations){
            if(reservation.getStartedAtTime().toLocalDateTime().getMonthValue() < now.getMonthValue()){
                updateReservationStatus(reservation, ReservationStatus.CANCELED);
            } else if (reservation.getStartedAtTime().toLocalDateTime().getDayOfMonth() < now.getDayOfMonth()){
                updateReservationStatus(reservation, ReservationStatus.CANCELED);
            } else {
                if(now.getHour() > reservation.getReservationHour()){
                    if(reservation.getReservationStatus() == ReservationStatus.SEATED){
                        updateReservationStatus(reservation, ReservationStatus.CHECK_ORDERED);
                    }
                    if(reservation.getReservationStatus() != ReservationStatus.CHECK_ORDERED && reservation.getReservationStatus() != ReservationStatus.FINISHED){
                        updateReservationStatus(reservation, ReservationStatus.CANCELED);
                    }
                }
                if(now.getHour() == reservation.getReservationHour() && now.getMinute() > 30) {
                    if (reservation.getReservationStatus() == ReservationStatus.OPEN) {
                        updateReservationStatus(reservation, ReservationStatus.CANCELED);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    @Override
    public void cleanMaybeReservations() {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.MAYBE_RESERVATION);
        LocalDateTime now = LocalDateTime.now();
        Restaurant restaurant = restaurantService.getRestaurantById(1).get();

        List<Reservation> allMaybeReservations = restaurant.getReservationsByStatusList(statusList);
        for (Reservation reservation : allMaybeReservations) {
            LocalDateTime tenMinutesLater = reservation.getStartedAtTime().toLocalDateTime().plusMinutes(10);
            if (now.compareTo(tenMinutesLater) > 0) {
                reservation.setReservationStatus(ReservationStatus.CANCELED);
            }
        }
    }


    @Transactional
    @Override
    public void applyDiscount(long reservationId) {
        Optional<Reservation> maybeReservation = reservationDao.getReservationById(reservationId);
        if (maybeReservation.isPresent()) {
            Reservation reservation = maybeReservation.get();
            Customer customer = customerService.getCustomerById(reservation.getCustomer().getId()).get();

            if (customer.getPoints() >= POINTS_TO_DISCOUNT) {
                customer.setPoints(customer.getPoints() - POINTS_TO_DISCOUNT);
                reservation.setReservationDiscount(true);
            }
        }

    }

    @Transactional
    @Override
    public void cancelDiscount(long reservationId) {
        Optional<Reservation> maybeReservation = reservationDao.getReservationById(reservationId);
        if (maybeReservation.isPresent()) {
            Reservation reservation = maybeReservation.get();
            Customer customer = customerService.getCustomerById(reservation.getCustomer().getId()).get();

            customer.setPoints(customer.getPoints() + POINTS_TO_DISCOUNT);
            reservation.setReservationDiscount(false);
        }
    }

    @Override
    public float getDiscountCoefficient(long reservationId) {
        Reservation reservation = reservationDao.getReservationById(reservationId).get();
        if (reservation.isReservationDiscount()) {
            return customerService.getDiscountCoefficient();
        }
        return 1f;
    }

    @Override
    public boolean canOrderReceipt(Reservation reservation, boolean hasOrdered) {
        return Objects.equals(reservation.getReservationStatus().getName(), "SEATED") && hasOrdered;
    }

    @Override
    public boolean isFromOrder(String isFromOrderP) {
        return isFromOrderP.compareTo("true") == 0;
    }
}
