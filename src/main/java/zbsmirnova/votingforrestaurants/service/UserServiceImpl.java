package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.AuthorizedUser;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.repository.UserRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFound;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository repository;

//    @Autowired
//    private final PasswordEncoder passwordEncoder;

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        if(!user.isNew() && get(user.getId()) == null) return null;
        return repository.save(user);
//        repository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User getWithVotes(int id) {
        return repository.getWithVotes(id);
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
