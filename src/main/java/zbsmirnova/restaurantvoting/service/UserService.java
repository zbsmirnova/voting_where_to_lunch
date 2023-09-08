package zbsmirnova.restaurantvoting.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.restaurantvoting.AuthorizedUser;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.repository.UserRepository;
import zbsmirnova.restaurantvoting.to.UserTo;
import zbsmirnova.restaurantvoting.util.UserUtil;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.util.List;

import static zbsmirnova.restaurantvoting.util.UserUtil.prepareToSave;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found user with id = " + id)), id);
    }

    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmailIgnoreCase(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email" + email + " is not found")), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public User save(@NotNull User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    @Transactional
    public void update(UserTo userTo, int id) {
        assureIdConsistent(userTo, id);
        Assert.notNull(userTo, "user must not be null");
        User user = get(id);
        repository.save(UserUtil.updateFromTo(user, userTo));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getUserByName(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " is not found"));
        return new AuthorizedUser(user);
    }
}