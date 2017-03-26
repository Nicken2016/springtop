package net.nicken.repository.mock;

import net.nicken.model.User;
import net.nicken.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository{
    private static final Logger LOG = getLogger(InMemoryUserRepositoryImpl.class);

    Map<Integer, User> repository = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger(0);

    private static final Comparator<User> USER_COMPARATOR = Comparator.comparing(User::getName).thenComparing(User::getEmail);

    @Override
    public User save(User user) {
        if(user.isNew())
            user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);
        return user;
    }


    @PostConstruct
    public void postConstruct(){
        LOG.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy(){
        LOG.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        return repository.values().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream()
                .sorted(USER_COMPARATOR)
                .collect(Collectors.toList());
    }

}
