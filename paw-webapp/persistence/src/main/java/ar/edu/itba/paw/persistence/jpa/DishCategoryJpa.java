package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.persistance.DishCategoryDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class DishCategoryJpa implements DishCategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<DishCategory> getDishCategoryByName(String categoryName) {
        final TypedQuery<DishCategory> query = em.createQuery("from DishCategory as dc where dc.name = :categoryName", DishCategory.class); //es hql, no sql
        query.setParameter("categoryName", categoryName);
        final List<DishCategory> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    @Override
    public Optional<DishCategory> getDishCategoryById(long id) {
        Optional<DishCategory> maybeDishCategory = Optional.of(em.find(DishCategory.class, id));
        return maybeDishCategory;
    }
}
