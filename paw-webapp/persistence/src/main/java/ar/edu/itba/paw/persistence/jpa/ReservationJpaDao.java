package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationJpaDao implements ReservationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Reservation> getReservationById(long id) {
        return Optional.of(em.find(Reservation.class, id));
    }

    @Override
    public Optional<Reservation> getReservationByIdAndStatus(long reservationId, List<ReservationStatus> status) {
        final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.reservationStatus in :status and r.id = :reservation_id",
                Reservation.class); //es hql, no sql
        query.setParameter("status", status);
        query.setParameter("reservation_id", reservationId);
        final List<Reservation> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    @Override
    public List<Reservation> getReservationsByStatusList(long restaurantId, List<ReservationStatus> statusList) {
        return null;
    }

    @Override
    public Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople, Timestamp startedAtTime) {
        final Reservation reservation = new Reservation(restaurant, customer, reservationHour, ReservationStatus.MAYBE_RESERVATION.ordinal(), qPeople, startedAtTime);
        em.persist(reservation);
        return reservation;
    }

    @Override
    public OrderItem createOrderItemByReservationId(long reservationId, Dish dish, int quantity) {
        return null;
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationId(long reservationId) {
        return null;
    }

    @Override
    public List<FullOrderItem> getOrderItemsByReservationIdAndStatus(long reservationId, List<OrderItemStatus> status) {
        return null;
    }

    @Override
    public List<FullOrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        return null;
    }

    @Override
    public void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus) {

    }

    @Override
    public void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus) {

    }

    @Override
    public void updateReservationStatus(long reservationId, ReservationStatus newStatus) {

    }

    @Override
    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {

    }

    @Override
    public void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId) {

    }

    @Override
    public List<Reservation> getAllReservations(long restaurantId) {
        final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.restaurant.id = :restaurant_id", Reservation.class); //es hql, no sql
        query.setParameter("restaurant_id", restaurantId);
        final List<Reservation> list = query.getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Reservation> getReservationsByCustomerId(long customerId) {
        return null;
    }

    @Override
    public void updateReservationById(long reservationId, long customerId, long hour, int qPeople) {

    }

    @Override
    public void applyDiscount(long reservationId) {

    }

    @Override
    public void cancelDiscount(long reservationId) {

    }

    @Override
    public List<Reservation> getReservationsByCustomerIdAndStatus(long customerId, List<ReservationStatus> statusList) {
        return null;
    }

    @Override
    public List<FullOrderItem> getAllOrderItems() {
        return null;
    }

    @Override
    public List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page) {
        return null;
    }
}
