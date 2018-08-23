package zbsmirnova.votingforrestaurants.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.votingforrestaurants.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer>{

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    User save(User user);

    @Override
    Optional<User> findById(Integer id);

    @Query("SELECT u FROM User u  ORDER BY u.name")
    List<User> getAll();

    User getByEmail(String email);
}
