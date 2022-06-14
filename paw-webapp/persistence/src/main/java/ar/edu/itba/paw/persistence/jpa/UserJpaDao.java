package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaDao implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(final String username, final String password, Roles role) {
        final User user = new User(username, password, role.getDescription());
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> findByName(final String username) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class); //es hql, no sql
        query.setParameter("username", username);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }
}
