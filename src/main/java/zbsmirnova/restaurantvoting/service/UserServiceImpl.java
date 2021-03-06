package zbsmirnova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
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
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFound;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found user with id = " + id)), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    @Transactional
    @Override
    public void update(UserTo userTo, int id) {
        Assert.notNull(userTo, "user must not be null");
        User user = get(id);
        repository.save(UserUtil.updateFromTo(user, userTo));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.getByEmail(s.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + s + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
