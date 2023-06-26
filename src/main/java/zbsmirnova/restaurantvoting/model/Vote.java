package zbsmirnova.restaurantvoting.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_unique_idx")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;


    @Column(name = "date", columnDefinition = "timestamp default current_date", nullable = false)
    @NotNull
    private LocalDate date;

    public Vote(LocalDate date) {
        super(null);
        this.date = date;
    }

    public Vote(int id, LocalDate date, User user, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(LocalDate date, User user, Restaurant restaurant) {
        super(null);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getDate(), vote.getUser(), vote.getRestaurant());
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user id =" + user.getId() +
                ", restaurant id =" + restaurant.getId() +
                ", vote date =" + date +
                '}';
    }
}
