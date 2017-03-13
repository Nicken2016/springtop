package net.nicken.repository.mock;

import jdk.nashorn.internal.objects.annotations.Constructor;
import net.nicken.model.Role;
import net.nicken.model.User;
import net.nicken.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
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

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    @Override
    public User save(User user) {
        Objects.requireNonNull(user);
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
        Objects.requireNonNull(email);
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
