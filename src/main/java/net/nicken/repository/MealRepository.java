package net.nicken.repository;

import net.nicken.model.Meal;

import java.text.CollationElementIterator;
import java.time.LocalDateTime;
import java.util.Collection;


public interface MealRepository {

    Meal save(Meal meal, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    default Meal getWithUser(int id, int userId){
        throw new UnsupportedOperationException();
    }
}
