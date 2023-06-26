package zbsmirnova.restaurantvoting.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import zbsmirnova.restaurantvoting.model.Role;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.to.UserTo;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class UserUtil {

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), Role.ROLE_USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(password.isEmpty() ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static List<UserTo> asTo(List<User> users) {
        return users.stream().map(UserUtil::asTo).collect(toList());
    }
}