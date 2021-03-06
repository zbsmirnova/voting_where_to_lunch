package zbsmirnova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.restaurantvoting.model.Restaurant;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findById(int id);

    @Query("SELECT r FROM Restaurant r  ORDER BY r.name")
    List<Restaurant> getAll();


    //SELECT a, p FROM Author a JOIN a.publications p ON p.publishingDate > ?1
//    @Query("select r, d from Restaurant  r join d on d.")
//    List<Restaurant> getAllWithTodayMenu();

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);
}
