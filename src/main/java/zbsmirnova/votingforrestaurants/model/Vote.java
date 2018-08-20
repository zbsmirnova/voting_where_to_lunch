package zbsmirnova.votingforrestaurants.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//??? надо ли
    @NotNull
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//???
    @NotNull
    private Restaurant restaurant;


    @Column(name = "voteDate", columnDefinition = "timestamp default current_date",  nullable = false)
    @NotNull
    private LocalDate voteDate = LocalDate.now();

    public Vote(){}

    public Vote(LocalDate date){
        super(null);
        this.voteDate = date;
    }

    public Vote(int id, LocalDate date, User user, Restaurant restaurant){
        super(id);
        this.voteDate = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(LocalDate date, User user, Restaurant restaurant){
        super(null);
        this.voteDate = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user id =" + user.getId() +
                ", restaurant id =" + restaurant.getId() +
                ", vote date =" + voteDate +
                '}';
    }
}
