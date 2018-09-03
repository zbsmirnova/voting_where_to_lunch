package zbsmirnova.votingforrestaurants.util;


import org.omg.CORBA.DynAnyPackage.Invalid;
import zbsmirnova.votingforrestaurants.HasId;
import zbsmirnova.votingforrestaurants.util.exception.InvalidVoteTimeException;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalTime;


public class ValidationUtil {
    private static final LocalTime STOP_VOTING_TIME = LocalTime.of(11, 00, 00, 00);

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFoundWithUserId(T object, int id) {
        return checkNotFound(object, "userId=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static void checkVotingTime(LocalTime voteTime){
        if(voteTime.isAfter(STOP_VOTING_TIME)){
            throw new InvalidVoteTimeException("it`s too late to vote, " + voteTime + ", try tomorrow before 11:00 a.m.");
        }
    }
}