package zbsmirnova.votingforrestaurants.to;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class VoteTo extends BaseTo implements Serializable{
    //private static final long serialVersionUID = 1L;

    @NotNull
    LocalDate voteDate;

//    @NotBlank
//    @Size(min = 2, max = 100)
//    String userName;
//
//    @NotBlank
//    @Size(min = 2, max = 100)
//    String restaurantName;

    @NotNull
    int userId;

    @NotNull
    int restaurantId;

    public VoteTo(){};

    public VoteTo(Integer id, LocalDate voteDate, int userId, int restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

//    public VoteTo(Integer id, LocalDate voteDate, String userName, String restaurantName) {
//        super(id);
//        this.voteDate = voteDate;
//        this.userName = userName;
//        this.restaurantName = restaurantName;
//    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    //    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getRestaurantName() {
//        return restaurantName;
//    }
//
//    public void setRestaurantName(String restaurantName) {
//        this.restaurantName = restaurantName;
//    }
}
