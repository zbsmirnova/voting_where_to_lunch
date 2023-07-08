package zbsmirnova.restaurantvoting.testData;

import org.springframework.test.web.servlet.ResultMatcher;
import zbsmirnova.restaurantvoting.model.Role;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.to.UserTo;
import zbsmirnova.restaurantvoting.web.json.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static zbsmirnova.restaurantvoting.TestUtil.START_SEQ_TEST;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeIgnoreProps;

public class UserTestData {
    public static final int USER1_ID = START_SEQ_TEST + 20;
    public static final int USER2_ID = START_SEQ_TEST + 21;
    public static final int ADMIN_ID = START_SEQ_TEST + 22;

    public static final User USER1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password", Role.USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@mail.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static List<User> ALL_USERS = Arrays.asList(ADMIN, USER1, USER2);

    public static User getCreatedUser() {
        return new User("new user", "newuser@mail.ru", "password", Role.USER);
    }

    public static User getUpdatedUser() {
        return new User(USER1_ID, "updated user", "user1@yandex.ru", "password", Role.USER);
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualTo(expected).isEqualToIgnoringGivenFields(expected, "password", "votes");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).isEqualTo(expected).usingElementComparatorIgnoringFields("password", "votes");
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static String jsonWithPassword(UserTo user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "password"));
    }

    public static ResultMatcher contentJson(UserTo... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "password"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "password"));
    }

    public static ResultMatcher contentJson(UserTo expected) {
        return content().json(writeIgnoreProps(expected, "password"));
    }
}
