package net.nicken.repository.jdbc;

import net.nicken.model.Meal;
import net.nicken.repository.MealRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository{

    @Override
    public Meal save(Meal meal, int userId) {
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return null;
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return null;
    }
}
