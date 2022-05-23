package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistance.CustomerDao;
import ar.edu.itba.paw.persistence.jdbc.CustomerJdbcDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerJpaDao implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return Optional.of(em.find(Customer.class, id));
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
    public void addPointsToCustomer(long customerId, int points) {

    }

    @Override
    public Customer create(String customerName, String phone, String mail, long id) {
        final Customer customer = new Customer(customerName, phone, mail, id, 0);
        em.persist(customer);
        return customer;
    }

    @Override
    public void linkCustomerToUserId(long customerId, long userId) {

    }

    @Override
    public void updatePoints(long customerId, int points) {

    }

    @Override
    public void updateCustomerData(long customerId, String name, String phone, String mail) {

    }
}
