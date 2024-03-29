package zbsmirnova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> getAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    List<Vote> getAllByDate(LocalDate date);

    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);
}
