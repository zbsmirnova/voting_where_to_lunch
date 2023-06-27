package zbsmirnova.restaurantvoting.to;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class VoteTo extends BaseTo implements Serializable {

    @NotNull
    private final LocalDate voteDate;

    private final int userId;

    private int restaurantId;

    public VoteTo(Integer id, @NotNull LocalDate voteDate, int userId, int restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
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