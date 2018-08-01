package zbsmirnova.votingforrestaurants.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.votingforrestaurants.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Override
    Vote save(Vote vote);

    List<Vote> getAllByRestaurantId(int restaurantId);

    List<Vote> getAllByUserId(int userId);

    List<Vote> getAllByVoteDate(LocalDate date);

    @EntityGraph(attributePaths = {"restaurants"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.id=?1")
    Vote getWithRestaurant(int id);


}
