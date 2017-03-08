package net.nicken.repository;

import net.nicken.model.Meal;
import java.util.Collection;


public interface MealRepository {
    Meal save(Meal meal);

    void delete(int id);

    Meal get(int id);

    Collection<Meal> getAll();
}
