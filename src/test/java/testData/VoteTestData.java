package testData;

import zbsmirnova.votingforrestaurants.model.Vote;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static testData.RestaurantTestData.BUSHE;
import static testData.RestaurantTestData.KETCHUP;
import static testData.RestaurantTestData.KFC;
import static testData.UserTestData.USER1;
import static testData.UserTestData.USER2;
import static zbsmirnova.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE_1_ID = START_SEQ + 20;
    public static final int VOTE_2_ID = START_SEQ + 21;
    public static final int VOTE_3_ID = START_SEQ + 22;


    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, Date.valueOf("2018-07-25"), USER1, KFC);
    public static final Vote VOTE_2 = new Vote(VOTE_2_ID, Date.valueOf("2018-07-25"), USER2, BUSHE);
    public static final Vote VOTE_3 = new Vote(VOTE_3_ID, Date.valueOf(LocalDate.now()), USER2, KETCHUP);

    public static final List<Vote> VOTES = Arrays.asList(VOTE_1, VOTE_2, VOTE_3);
}
