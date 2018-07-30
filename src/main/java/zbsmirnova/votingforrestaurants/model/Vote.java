package zbsmirnova.votingforrestaurants.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//??? надо ли
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//???
    @NotNull
    private Restaurant restaurant;

    @Column(name = "voting_date", columnDefinition = "timestamp default current_date",  nullable = false)
    @NotNull
    private Date voting_date = new Date();

    public Vote(){}

    public Vote(int id, Date date, User user, Restaurant restaurant){
        super(id);
        this.voting_date = date;
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

    public Date getVoting_date_time() {
        return voting_date;
    }

    public void setVoting_date_time(Date voting_date_time) {
        this.voting_date = voting_date_time;
    }
}
