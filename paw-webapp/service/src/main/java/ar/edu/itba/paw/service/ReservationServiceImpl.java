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

import java.time.Instant;
import java.time.LocalDateTime;

import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationDao reservationDao;
    private final RestaurantDao restaurantDao;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final MailingService mailingService;
    private static final int POINTS_TO_DISCOUNT = 100;

    @Autowired
    public ReservationServiceImpl(final ReservationDao reservationDao, final RestaurantDao restaurantDao,
                                    final CustomerService customerService, final RestaurantService restaurantService, final MailingService mailingService) {
        this.reservationDao = reservationDao;
        this.restaurantDao = restaurantDao;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.mailingService = mailingService;
    }

    @Transactional
    @Override
    public Optional<Reservation> getReservationBySecurityCode(String securityCode) {
        return reservationDao.getReservationBySecurityCode(securityCode);
    }

    @Transactional
    @Override
    public Optional<Reservation> getReservationByIdAndIsActive(String securityCode) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.SEATED);

        return reservationDao.getReservationBySecurityCodeAndStatus(securityCode, statusList);
    }

    @Transactional
    @Override
    public Optional<Reservation> getReservationBySecurityCodeAndStatus(String securityCode, ReservationStatus status) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(status);

        return reservationDao.getReservationBySecurityCodeAndStatus(securityCode, statusList);
    }

    @Transactional
    @Override
    public void updateReservationById(Reservation reservation, Customer customer, long hour, int qPeople) {
        reservation.setReservationHour((int) hour);
        reservation.setCustomer(customer);
        reservation.setqPeople(qPeople);
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
    public void updateReservationDateById(Reservation reservation, LocalDateTime reservationDate) {
        reservation.setReservationDate(reservationDate);
    }

    @Transactional
    @Override
    public Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople) {
        Reservation reservation = customer.createReservation(restaurant, customer, reservationHour, qPeople, LocalDateTime.now(), LocalDateTime.now());
        return reservation;
    }

    @Transactional
    @Override
    public void setReservationSecurityCode(Reservation reservation) {
        String securityCode = ServiceUtils.generateReservationSecurityCode(reservation);
        reservation.setSecurityCode(securityCode);
    }

    @Transactional
    @Override
    public void raiseHand(String reservationIdP) {
        Optional<Reservation> reservation = reservationDao.getReservationBySecurityCode(reservationIdP);
        reservation.ifPresent(value -> value.setHand(!value.isHand()));
    }

    @Override
    public boolean isRepeating(Customer customer, Reservation reservation) {
        List<Reservation> reservations = customer.getReservationsByStatusList(Collections.singletonList(ReservationStatus.OPEN));
        for(Reservation reservation1 : reservations){
            if(reservation1.getReservationDate().equals(reservation.getReservationDate())){
                if(reservation1.getReservationHour() == reservation.getReservationHour()) {
                    if (!Objects.equals(reservation1.getSecurityCode(), reservation.getSecurityCode())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void finishReservation(Restaurant restaurant, Customer customer, Reservation reservation) {
        updateReservationById(reservation, customer, reservation.getReservationHour(), reservation.getqPeople());
        updateReservationStatus(reservation, ReservationStatus.OPEN);
        mailingService.sendConfirmationEmail(restaurant, customer, reservation);
    }

    @Transactional
    @Override
    public void cancelReservation(Restaurant restaurant, Customer customer, Reservation reservation) {
        updateReservationStatus(reservation, ReservationStatus.CANCELED);
        mailingService.sendCancellationEmail(restaurant,customer,reservation);
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
    public List<Integer> getAvailableHours(long restaurantId, long qPeople, LocalDateTime reservationDate) {
        Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId).get();
        //List<Reservation> reservations = restaurant.getReservations();

        LocalDateTime now = LocalDateTime.now();
        List<Reservation> reservations = reservationDao.getReservationsToCalculateAvailableTables(restaurantId, reservationDate);

        if(reservationDate.isBefore(now)){
            return new ArrayList<>();
        }

        int i;
        int openHour = restaurant.getOpenHour();
        int closeHour = restaurant.getCloseHour();

        List<Integer> totalHours = new ArrayList<>();
        if (openHour < closeHour) {
            for(i = openHour; i < closeHour; i++) {
                totalHours.add(i);
            }
        } else if( closeHour < openHour ) {
            for (i = openHour; i<24; i++) {
                totalHours.add(i);
            }
            for (i = 0; i < closeHour; i++) {
                totalHours.add(i);
            }
        }

        List<Integer> notAvailable = new ArrayList<>();
        if(now.getDayOfMonth() == reservationDate.getDayOfMonth()){
            totalHours.removeIf(hour -> hour <= LocalDateTime.now().getHour());
        }

        Map<Integer, Integer> map = new HashMap<>();
        for(Reservation reservation :reservations){
            if(map.containsKey(reservation.getReservationHour())){
                map.put(reservation.getReservationHour(), map.get(reservation.getReservationHour())+reservation.getqPeople());
            } else {
                map.put(reservation.getReservationHour(), reservation.getqPeople());
            }
        }
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
        return totalHours;
    }

    @Transactional
    @Override
    public List<Long> getUnavailableItems(long reservationId) {
        Optional<Reservation> maybeRes =  reservationDao.getReservationById(reservationId);
        if(! maybeRes.isPresent()){
            return new ArrayList<>();
        }

        Reservation reservation = maybeRes.get();
        List<OrderItem> query = reservationDao.getOrderItems(reservation.getId());

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
    public List<Reservation> getReservationsByCustomerAndActive(Customer customer) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.OPEN);
        statusList.add(ReservationStatus.SEATED);
        return customer.getReservationsByStatusList(statusList);
    }

    @Transactional
    @Override
    public List<Reservation> getReservationsByCustomerAndStatus(Customer customer, ReservationStatus status) {
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(status);
        return customer.getReservationsByStatusList(statusList);
    }

    @Transactional
    @Override
    public void setTableNumber(Reservation reservation, int number) {
        reservation.setTableNumber(number);
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
        statusList.add(OrderItemStatus.CHECK_ORDERED);

        return reservationDao.getOrderItemsByStatusListAndReservation(reservation.getId(), statusList);
    }

    @Transactional
    @Override
    public void updateOrderItemsStatus(Reservation reservation, OrderItemStatus oldStatus, OrderItemStatus newStatus) {
        reservationDao.getOrderItems(reservation.getId()).forEach(o -> {
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
        reservationDao.getOrderItems(reservation.getId()).forEach(o -> {
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



    @Scheduled(cron = "0 1/31 * * * ?")
    @Transactional
    @Override
    public void checkReservationTime() {
        LocalDateTime now = LocalDateTime.now();
        Restaurant restaurant = restaurantService.getRestaurantById(1).get();

        //List<Reservation> allReservations = restaurant.getReservations();
        List<Reservation> allReservations = reservationDao.getReservationsOfToday(1);

        for(Reservation reservation :allReservations){
            if(reservation.getReservationStatus() != ReservationStatus.FINISHED && reservation.getReservationStatus() != ReservationStatus.CANCELED) {
                if (reservation.getReservationDate().getYear() < now.getYear()) {
                    updateReservationStatus(reservation, ReservationStatus.CANCELED);
                } else if (reservation.getReservationDate().getMonthValue() < now.getMonthValue()) {
                    updateReservationStatus(reservation, ReservationStatus.CANCELED);
                } else if (reservation.getReservationDate().getDayOfMonth() < now.getDayOfMonth()) {
                    updateReservationStatus(reservation, ReservationStatus.CANCELED);//
                } else {
                    reservation.setIsToday(true);
                    if (now.getHour() > reservation.getReservationHour()) {
                        if (reservation.getReservationStatus() == ReservationStatus.SEATED) {
                            updateReservationStatus(reservation, ReservationStatus.CHECK_ORDERED);
                        }
                        if (reservation.getReservationStatus() != ReservationStatus.CHECK_ORDERED) {
                            updateReservationStatus(reservation, ReservationStatus.CANCELED);//
                        }
                    }
                    if (now.getHour() == reservation.getReservationHour() && now.getMinute() > 30) {
                        if (reservation.getReservationStatus() == ReservationStatus.OPEN) {
                            updateReservationStatus(reservation, ReservationStatus.CANCELED);
                        }
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    @Override
    public void cleanMaybeReservations() {
        LocalDateTime now = LocalDateTime.now();
        Restaurant restaurant = restaurantService.getRestaurantById(1).get();

        List<Reservation> allMaybeReservations = restaurant.getReservationsByStatusList(Collections.singletonList(ReservationStatus.MAYBE_RESERVATION));
        for (Reservation reservation : allMaybeReservations) {
            LocalDateTime tenMinutesLater = reservation.getStartedAtTime().plusMinutes(10);
            if (now.compareTo(tenMinutesLater) > 0) {
                reservation.setReservationStatus(ReservationStatus.CANCELED);
            }
        }
    }


    @Transactional
    @Override
    public void applyDiscount(String reservationSecurityCode) {
        Optional<Reservation> maybeReservation = reservationDao.getReservationBySecurityCode(reservationSecurityCode);
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
    public void cancelDiscount(String reservationSecurityCode) {
        Optional<Reservation> maybeReservation = reservationDao.getReservationBySecurityCode(reservationSecurityCode);
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
