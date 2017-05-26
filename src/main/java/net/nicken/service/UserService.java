package net.nicken.service;

import net.nicken.model.User;
import net.nicken.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User save(User user);

    void delete(int id);

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();

    void update(User user);

    void evictCache();

    User getWithMeals(int id);

}
