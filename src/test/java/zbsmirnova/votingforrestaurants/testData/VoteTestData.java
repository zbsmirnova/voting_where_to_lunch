package zbsmirnova.votingforrestaurants.testData;

import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.model.Vote;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER2;
import static zbsmirnova.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE_1_ID = START_SEQ + 31;
    public static final int VOTE_2_ID = START_SEQ + 32;
    public static final int VOTE_3_ID = START_SEQ + 33;


    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, LocalDate.parse("2018-07-25"), USER1, KFC);
    public static final Vote VOTE_2 = new Vote(VOTE_2_ID, LocalDate.parse("2018-07-25"), USER2, BUSHE);
    public static final Vote VOTE_3 = new Vote(VOTE_3_ID, LocalDate.now(), USER2, KETCHUP);

    public static final List<Vote> ALL_VOTES = Arrays.asList(VOTE_1, VOTE_2, VOTE_3);

    public static Vote getCreatedVote(){return new Vote(LocalDate.now());}

    public static Vote getUpdatedVote(){
        VOTE_1.setRestaurant(MCDONALDS);
        return VOTE_1;
    }

    public static Vote getDuplicateUserIdDateVote(){
        return new Vote(LocalDate.parse("2018-07-25"));
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "user");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "user").isEqualTo(expected);
    }
}
