package zbsmirnova.votingforrestaurants.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.votingforrestaurants.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r  ORDER BY r.name")
    List<Restaurant> getAll();

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);


    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);


    Optional<Restaurant> findById(Integer id);

}
