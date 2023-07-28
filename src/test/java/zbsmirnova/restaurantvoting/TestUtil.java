package zbsmirnova.restaurantvoting;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeIgnoreProps;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeValue;

public class TestUtil {

    private static final LocalDateTime allowedTime = LocalDateTime.of(2023, 8, 23, 10, 30);
    private static final LocalDateTime prohibitedTime = allowedTime.withHour(11);
    private static final ZoneId zoneId = ZoneId.systemDefault();
    public static final Clock ALLOWED_TIME_CLOCK = Clock.fixed(allowedTime.atZone(zoneId).toInstant(), zoneId);
    public static final Clock PROHIBITED_TIME_CLOCK = Clock.fixed(prohibitedTime.atZone(zoneId).toInstant(), zoneId);

    public static final int START_SEQ_TEST = 10000;
    public static final int NOT_EXISTING_ENTITY_ID = 1;

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static void print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action.andReturn()), clazz);
    }

    private static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeValue(expected));
    }

    public static <T> ResultMatcher contentJson(T expected, String... props) {
        return content().json(writeIgnoreProps(expected, props));
    }

    @SafeVarargs
    public static <T> ResultMatcher contentJsonArray(T... expected) {
        return contentJson(expected);
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }
}
