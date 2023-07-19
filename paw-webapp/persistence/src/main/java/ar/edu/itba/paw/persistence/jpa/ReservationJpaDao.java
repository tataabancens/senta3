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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ReservationJpaDao implements ReservationDao {

    private final static int PAGE_SIZE = 30;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Reservation> getReservationBySecurityCode(String securityCode) {
        final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.securityCode = :reservation_security_code",
                Reservation.class); //es hql, no sql
        query.setParameter("reservation_security_code", securityCode);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<Reservation> getReservationById(long id) {
        return Optional.ofNullable(em.find(Reservation.class, id));
    }


    @Override
    public Optional<OrderItem> getOrderItemById(long orderItemId) {
        return Optional.ofNullable(em.find(OrderItem.class, orderItemId));
    }

    @Override
    public Optional<Reservation> getReservationBySecurityCodeAndStatus(String securityCode, List<ReservationStatus> status) {
        if(status.isEmpty()){
            return Optional.empty();
        }
        final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.reservationStatus in :status and r.securityCode = :reservation_security_code",
                Reservation.class); //es hql, no sql
        query.setParameter("status", status);
        query.setParameter("reservation_security_code", securityCode);
        final List<Reservation> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    @Override
    public List<Reservation> getAllReservationsOrderedBy(long restaurantId, String orderBy, String direction, String filterStatus, int page, long customerId) {
        //preparo el sql string
        String filterStatusString = "";
        String filterCustomerIdString = "";
        if(!Objects.equals(orderBy, "reservationid") && !Objects.equals(orderBy, "customerid") && !Objects.equals(orderBy, "qpeople") && !Objects.equals(orderBy, "reservationhour") && !Objects.equals(orderBy, "reservationdate") && !Objects.equals(orderBy, "reservationstatus") && !Objects.equals(orderBy, "tablenumber")) {
            return new ArrayList<>();
        } else if(!Objects.equals(direction, "ASC") && !Objects.equals(direction, "DESC")) {
            return new ArrayList<>();
        } else if(!filterStatus.matches("[0-9]*")) {
            return new ArrayList<>();
        }
        if(customerId != 0){
            filterCustomerIdString = " AND customerId = " + customerId;
        }
        if(!Objects.equals(filterStatus, "9")){
            filterStatusString = " AND reservationStatus = " + filterStatus;
        }
        String orderByString = orderBy;
        if(Objects.equals(orderBy, "reservationdate")){
            orderByString += " " + direction + ", reservationhour";
        }


        //técnica 1+1
        final Query idQuery = em.createNativeQuery("SELECT reservationid FROM reservation NATURAL JOIN customer CROSS JOIN restaurant WHERE restaurant.restaurantid = :restaurantId"
                + filterCustomerIdString + filterStatusString +
                " ORDER BY " + orderByString + " " + direction + " OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY");
        idQuery.setParameter("restaurantId", restaurantId);
        idQuery.setParameter("offset", Math.abs((page-1)*10));
        idQuery.setParameter("pageSize", PAGE_SIZE);

        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if(! ids.isEmpty()) {
            final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.id IN :ids order by " + orderByString + " " + direction, Reservation.class); //es hql, no sql
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
    public List<OrderItem> getOrderItems(Long reservationId) {
        final Query idQuery = em.createNativeQuery("SELECT id FROM orderItem WHERE reservationid = :reservationid ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY");
        idQuery.setParameter("reservationid", reservationId);
        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());


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
    public List<Reservation> getReservationsToCalculateAvailableTables(long restaurantId, LocalDateTime reservationDate) {
        final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.reservationStatus NOT IN :statusList and r.reservationDate between :start and :finish", Reservation.class); //es hql, no sql
        List<ReservationStatus> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.CANCELED);
        statusList.add(ReservationStatus.FINISHED);
        query.setParameter("statusList", statusList);
        query.setParameter("start", reservationDate.minusDays(1));
        query.setParameter("finish", reservationDate);
        final List<Reservation> list = query.getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Reservation> getReservationsOfToday(long restaurantId) {
        final TypedQuery<Reservation> query = em.createQuery("from Reservation as r where r.reservationDate between :start and :finish", Reservation.class); //es hql, no sql
        query.setParameter("start", LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        query.setParameter("finish", LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT));

        final List<Reservation> list = query.getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public void deleteCustomer(String securityCode) {
        Optional<Reservation> maybeReservation = getReservationBySecurityCode(securityCode);
        maybeReservation.ifPresent(r -> em.remove(r));
    }

    @Override
    public void deleteAllOrderItems(String securityCode) {
        Optional<Reservation> maybeReservation = getReservationBySecurityCode(securityCode);
        if(!maybeReservation.isPresent()){
            return;
        }
        List<OrderItem> orderItems = getOrderItems(maybeReservation.get().getId());
        if(orderItems.isEmpty()){
            return;
        }



    }

    @Override
    public List<OrderItem> getOrderItemsByStatusListAndReservationStatusList(List<Integer> reservationStauses, List<Integer> orderItemStatuses, String securityCode) {
        String queryString = "SELECT id FROM orderItem NATURAL JOIN dish NATURAL JOIN reservation WHERE status IN :orderItemStatus AND reservation.reservationStatus IN :reservationStatus ";

        if (!Objects.equals(securityCode, "")) {
            queryString += "AND securitycode = :securitycode" ;
        }

        queryString += " ORDER BY id OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY";

        //técnica 1+1
        final Query idQuery = em.createNativeQuery(queryString);
        idQuery.setParameter("orderItemStatus", orderItemStatuses);
        idQuery.setParameter("reservationStatus", reservationStauses);
        if (!Objects.equals(securityCode, "")) {
            idQuery.setParameter("securitycode", securityCode);
        }
        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

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
    public int getTotalPages(long restaurantId, String filterStatus, long customerId) {
        //preparo el sql string
        String filterStatusString = "";
        String filterCustomerIdString = "";
        if(!filterStatus.matches("[0-9]*")) {
            return 0;
        }
        if(customerId != 0){
            filterCustomerIdString = " AND customerId = " + customerId;
        }
        if(!Objects.equals(filterStatus, "9")){
            filterStatusString = " AND reservationStatus = " + filterStatus;
        }
        //técnica 1+1
        final Query idQuery = em.createNativeQuery("SELECT reservationid FROM reservation NATURAL JOIN customer CROSS JOIN restaurant WHERE restaurant.restaurantid = :restaurantId"
                + filterCustomerIdString + filterStatusString);

        idQuery.setParameter("restaurantId", restaurantId);

        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        return (int) Math.ceil((float) ids.size() / PAGE_SIZE);
    }

    @Override
    public List<OrderItem> getOrderItemsByStatusListAndReservation(Long reservationId, List<OrderItemStatus> statusList) {
        if(statusList.isEmpty()){
            return new ArrayList<>();
        }
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

}
