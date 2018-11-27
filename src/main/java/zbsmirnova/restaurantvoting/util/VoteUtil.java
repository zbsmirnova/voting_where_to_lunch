package zbsmirnova.restaurantvoting.util;

import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class VoteUtil {
    public static Vote createNew() {
        return new Vote(LocalDate.now());
    }

    public static Vote updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setDate(voteTo.getVoteDate());
        return vote;
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate(), vote.getUser().getId(), vote.getRestaurant().getId());
    }
    public static List<VoteTo> asTo(List<Vote> votes){
        return votes.stream().map(VoteUtil::asTo).collect(toList());
    }

}
