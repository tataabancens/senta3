package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.persistance.ReservationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        //preparo el sql string
        String filterStatusString = "";
        if(!Objects.equals(orderBy, "reservationid") && !Objects.equals(orderBy, "customerid") && !Objects.equals(orderBy, "qpeople") && !Objects.equals(orderBy, "reservationhour") && !Objects.equals(orderBy, "reservationstatus")) {
            return new ArrayList<>();
        } else if(!Objects.equals(direction, "ASC") && !Objects.equals(direction, "DESC")) {
            return new ArrayList<>();
        } else if(!filterStatus.matches("[0-9]*")) {
            return new ArrayList<>();
        }
        if(!Objects.equals(filterStatus, "9")){
            filterStatusString = " AND reservationStatus = " + filterStatus;
        }

        //técnica 1+1
        final Query idQuery = em.createNativeQuery("SELECT reservationid FROM reservation NATURAL JOIN customer CROSS JOIN restaurant WHERE restaurant.restaurantid = :restaurantId" + filterStatusString +
                " ORDER BY " + orderBy + " " + direction + " OFFSET :offset ROWS FETCH NEXT 10 ROWS ONLY");
        idQuery.setParameter("restaurantId", restaurantId);
        idQuery.setParameter("offset", Math.abs((page-1)*10));

        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if(! ids.isEmpty()) {
            final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.id IN :ids order by " + orderBy + " " + direction, Reservation.class); //es hql, no sql
            query.setParameter("ids", ids);
            final List<Reservation> list = query.getResultList();
            return list.isEmpty() ? new ArrayList<>() : list;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByStatus(OrderItemStatus status) {
        //técnica 1+1
        final Query idQuery = em.createNativeQuery("SELECT id FROM orderItem NATURAL JOIN dish WHERE status = :status ORDER BY id OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY");
        idQuery.setParameter("status", status.ordinal());
        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());
        /*
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());
         */
        if(! ids.isEmpty()){
            final TypedQuery<OrderItem> query = em.createQuery("from OrderItem as o where o.id IN :ids order by id", OrderItem.class); //es hql, no sql
            query.setParameter("ids", ids);
            final List<OrderItem> list = query.getResultList();
            return list.isEmpty() ? new ArrayList<>() : list;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<OrderItem> getOrderItems(Reservation reservation) {
        //falta 1+1 TODO
        final Query idQuery = em.createNativeQuery("SELECT id FROM orderItem WHERE reservationid = :reservationid ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY");
        idQuery.setParameter("reservationid", reservation.getId());
        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        //final TypedQuery<OrderItem> query = em.createQuery("from OrderItem as o", OrderItem.class); //es hql, no sql
        //query.setMaxResults(200);
        //query.setFirstResult(0);
        //final List<OrderItem> list = query.getResultList();
        //return list.isEmpty() ? new ArrayList<>() : list;
        if(! ids.isEmpty()){
            final TypedQuery<OrderItem> query = em.createQuery("from OrderItem as o where o.id IN :ids order by id", OrderItem.class); //es hql, no sql
            query.setParameter("ids", ids);
            final List<OrderItem> list = query.getResultList();
            return list.isEmpty() ? new ArrayList<>() : list;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByStatusListAndReservation(Long reservationId, List<OrderItemStatus> statusList) {
        final Query idQuery = em.createNativeQuery("SELECT id FROM orderItem WHERE reservationid = :reservationid ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY");
        idQuery.setParameter("reservationid", reservationId);
        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if(! ids.isEmpty()){
            final TypedQuery<OrderItem> query = em.createQuery("from OrderItem as o where o.status in :statusList and o.id IN :ids", OrderItem.class); //es hql, no sql
            query.setParameter("statusList", statusList);
            query.setParameter("ids", ids);
            final List<OrderItem> list = query.getResultList();
            return list.isEmpty() ? new ArrayList<>() : list;
        } else {
            return new ArrayList<>();
        }
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
