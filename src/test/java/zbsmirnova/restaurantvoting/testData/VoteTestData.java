package zbsmirnova.restaurantvoting.testData;

import org.springframework.test.web.servlet.ResultMatcher;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.to.VoteTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static zbsmirnova.restaurantvoting.TestUtil.START_SEQ_TEST;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.testData.UserTestData.USER1;
import static zbsmirnova.restaurantvoting.testData.UserTestData.USER2;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeValue;

public class VoteTestData {
    public static final int VOTE_1_ID = START_SEQ_TEST + 23;
    public static final int VOTE_2_ID = START_SEQ_TEST + 24;
    public static final int VOTE_3_ID = START_SEQ_TEST + 25;


    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, LocalDate.parse("2018-07-25"), USER1, KFC);
    public static final Vote VOTE_2 = new Vote(VOTE_2_ID, LocalDate.parse("2018-07-25"), USER2, BUSHE);
    public static final Vote VOTE_3 = new Vote(VOTE_3_ID, LocalDate.now(), USER2, KETCHUP);

    public static final List<Vote> ALL_VOTES = Arrays.asList(VOTE_1, VOTE_2, VOTE_3);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(VoteTo expected) {
        return content().json(writeValue(expected));
    }

    public static ResultMatcher contentJson(VoteTo... expected) {
        return content().json(writeValue(Arrays.asList(expected)));
    }

}
