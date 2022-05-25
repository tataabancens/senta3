package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public Optional<OrderItem> getOrderItemById(long orderItemId) {
        return Optional.of(em.find(OrderItem.class, orderItemId));
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
    public List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page) {
        // Esto es dummy hay que ver como se soluciona
        final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.restaurant.id = :restaurant_id", Reservation.class); //es hql, no sql
        query.setParameter("restaurant_id", restaurantId);
        query.setMaxResults(10);
        query.setFirstResult(Math.abs(page-1)*10);
        final List<Reservation> list = query.getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<OrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        final TypedQuery<OrderItem> query = em.createQuery("from OrderItem as o where o.status = :status", OrderItem.class); //es hql, no sql
        query.setParameter("status", status);
        final List<OrderItem> list = query.getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<OrderItem> getOrderItems() {
        final TypedQuery<OrderItem> query = em.createQuery("from OrderItem as o", OrderItem.class); //es hql, no sql
        query.setMaxResults(100);
        query.setFirstResult(0);
        final List<OrderItem> list = query.getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<OrderItem> getOrderItemsByStatusList(List<OrderItemStatus> statusList) {
        final TypedQuery<OrderItem> query = em.createQuery("from OrderItem as o where o.status = :status", OrderItem.class); //es hql, no sql
        query.setParameter("status", statusList);
        query.setMaxResults(100);
        query.setFirstResult(0);
        final List<OrderItem> list = query.getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

//
//    @Override
//    public void updateOrderItemsStatus(long reservationId, OrderItemStatus oldStatus, OrderItemStatus newStatus) {
//
//    }
//
//    @Override
//    public void updateOrderItemStatus(long orderItemId, OrderItemStatus newStatus) {
//
//    }
//
//
//    @Override
//    public void deleteOrderItemsByReservationIdAndStatus(long reservationId, OrderItemStatus status) {
//
//    }
//
//    @Override
//    public void deleteOrderItemByReservationIdAndStatus(long reservationId, OrderItemStatus status, long orderItemId) {
//
//    }


}
