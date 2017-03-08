package net.nicken.repository.mock;

import net.nicken.model.Role;
import net.nicken.model.User;
import net.nicken.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository{
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    Map<Integer, User> repository = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        if(user.isNew())
            user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        if(!repository.containsKey(id))
            return false;
        repository.remove(id);
        return true;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        Collection<User> users = repository.values();
        return users.stream()
                .filter(m -> m.getEmail().equals(email))
                .findAny().get();
    }

    @Override
    public List<User> getAll() {
        return (List)repository.values();
    }

}
