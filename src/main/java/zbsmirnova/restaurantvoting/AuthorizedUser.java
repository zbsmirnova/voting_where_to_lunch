package zbsmirnova.restaurantvoting;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.to.UserTo;
import zbsmirnova.restaurantvoting.util.UserUtil;


import java.io.Serial;
import java.util.Collections;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, Collections.singleton(user.getRole()));
        this.userTo = UserUtil.asTo(user);
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public int getId() {
        return userTo.getId();
    }

    public static int id() {
        return get().userTo.getId();
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}