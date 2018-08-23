package zbsmirnova.votingforrestaurants.util;

import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class VoteUtil {
    public static Vote createNew() {
        return new Vote(LocalDate.now());
    }

    public static Vote updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setVoteDate(voteTo.getVoteDate());
        return vote;
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVoteDate(), vote.getRestaurant().getName(), vote.getUser().getName());
    }
    public static List<VoteTo> asTo(List<Vote> votes){
        return votes.stream().map(VoteUtil::asTo).collect(toList());
    }

}
