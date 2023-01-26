package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.persistance.CustomerDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class CustomerJpaDao implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }

    @Override
    public Customer create(String customerName, String phone, String mail) {
        final Customer customer = new Customer(customerName, phone, mail, 0);
        em.persist(customer);
        return customer;
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        final TypedQuery<Customer> query = em.createQuery("from Customer as c where c.user.username = :username", Customer.class); //es hql, no sql
        query.setParameter("username", username);
        final List<Customer> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    @Override
    public Customer create(String customerName, String phone, String mail, long id) {
        final Customer customer = new Customer(customerName, phone, mail, id, 0);
        em.persist(customer);
        return customer;
    }

    @Override
    public void deleteCustomer(long id) {
        Optional<Customer> maybeCustomer = getCustomerById(id);
        maybeCustomer.ifPresent(customer -> em.remove(customer));
    }

    @Override
    public List<Customer> getCustomers(int page){
        final Query idQuery = em.createNativeQuery("SELECT customerid FROM customer OFFSET :offset ROWS FETCH NEXT 10 ROWS ONLY");
        idQuery.setParameter("offset", Math.abs((page-1)*10));

        final List<Long> ids = (List<Long>) idQuery.getResultList().stream()
                .map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if(! ids.isEmpty()) {
            final TypedQuery<Customer> query = (TypedQuery<Customer>) em.createQuery("from Customer as c where c.id IN :ids"); //es hql, no sql
            query.setParameter("ids", ids);
            final List<Customer> list = query.getResultList();
            return list.isEmpty() ? new ArrayList<>() : list;
        } else {
            return new ArrayList<>();
        }
//
//        final List<Customer> customerList = query.getResultList();
//        return customerList.isEmpty() ? new ArrayList<>() : customerList;
    }
}
