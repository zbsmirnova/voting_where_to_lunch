package zbsmirnova.votingforrestaurants.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.votingforrestaurants.model.Menu;

import java.time.LocalDate;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Override
    Menu save(Menu menu);

//    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
////    @Query("SELECT m FROM Menu m WHERE m.id=?1")
////    Menu getWithDishes(int id);

    List<Menu> findAllByRestaurantId(int restaurantId);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT m FROM Menu m WHERE m.restaurant.id=?1 AND m.date=?2")
    Menu findByRestaurantIdAndAddDate(int restaurantId, LocalDate date);


}
