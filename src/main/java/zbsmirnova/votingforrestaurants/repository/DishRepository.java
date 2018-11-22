package zbsmirnova.votingforrestaurants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.votingforrestaurants.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    Optional<Dish> getByIdAndRestaurantId(int dishId, int restaurantId);

    List<Dish> findAllByRestaurantId(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = ?1 and d.date = ?2")
    List<Dish> getTodayMenu(int restaurantId, LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Transactional
    @Override
    Dish save(Dish dish);
}
