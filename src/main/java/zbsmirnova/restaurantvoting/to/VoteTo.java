package zbsmirnova.restaurantvoting.to;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class VoteTo extends BaseTo implements Serializable {

    @NotNull
    private LocalDate voteDate;

    @NotNull
    private int userId;

    @NotNull
    private int restaurantId;

    public VoteTo(Integer id, LocalDate voteDate, int userId, int restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "VoteTo{" + "id = " + id + ", userId =" + userId + '\'' + ", restaurantId = " + restaurantId + '\'' + ", date =" + voteDate + '\'' + '}';
    }
}