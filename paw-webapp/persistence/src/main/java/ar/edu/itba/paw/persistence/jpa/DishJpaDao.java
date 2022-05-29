package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.persistance.DishDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.Optional;

@Repository
public class DishJpaDao implements DishDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Dish> getDishById(long id) {
        return Optional.of(em.find(Dish.class, id));
    }

    @Override
    public void updateDishPhoto(long dishId, long imageId) {

    }

    @Override
    public void deleteDish(long dishId) {

    }

    @Override
    public Optional<Dish> getRecommendedDish(long reservationId) {
        final Query idQuery = em.createNativeQuery("With CTE AS (\n" +
                "    With currentOrderedCTE AS\n" +
                "             (select dishId, reservation.reservationid\n" +
                "              from orderitem, reservation\n" +
                "              where orderitem.reservationid = :reservationid and reservation.reservationid = orderitem.reservationid\n" +
                "              group by dishid, reservation.reservationid)\n" +
                "\n" +
                "        (select customersOrdered.dishId as defDishId, sum(customersOrdered.sum) as defsum\n" +
                "         from (select dishid, sum(quantity), reservation.reservationId\n" +
                "               from orderitem, reservation\n" +
                "               where orderitem.reservationid = reservation.reservationid\n" +
                "               group by dishid, reservation.reservationid) as customersOrdered (dishId, sum, reservationId),\n" +
                "              currentOrderedCTE as currentOrdered (currentDish, currentReservationId)\n" +
                "         where currentReservationId <> customersOrdered.reservationId\n" +
                "           and dishId not in (select dishId from currentOrderedCTE)\n" +
                "           and dishId in (select dishid\n" +
                "                          from orderitem, reservation\n" +
                "                          where orderitem.reservationid = reservation.reservationid and reservation.reservationid = customersOrdered.reservationId\n" +
                "                            and exists(\n" +
                "                                  select dishid, myReservation.reservationid\n" +
                "                                  from orderitem as myOrderItem, reservation as myReservation\n" +
                "                                  where myOrderItem.reservationid = myReservation.reservationid and myReservation.reservationid = customersOrdered.reservationId\n" +
                "                                    and exists(select dishid\n" +
                "                                               from (select * from currentOrderedCTE) as currentDishes\n" +
                "                                               where currentDishes.dishid = myOrderItem.dishid)\n" +
                "                                  group by myOrderItem.dishid, myReservation.reservationid)\n" +
                "                          group by dishid, reservation.reservationid)\n" +
                "         group by customersOrdered.dishId))\n" +
                "SELECT max(defDishId)\n" +
                "                          FROM CTE\n" +
                "                          where defSum >= ALL (select defsum\n" +
                "                                               from CTE)");
        idQuery.setParameter("reservationid", reservationId);

        final Long id = Integer.toUnsignedLong((Integer)idQuery.getResultList().stream().findFirst().get());


        final TypedQuery<Dish> query = em.createQuery("from Dish where id = :id", Dish.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst();
    }
}
