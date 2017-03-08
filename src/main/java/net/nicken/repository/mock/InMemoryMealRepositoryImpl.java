package net.nicken.repository.mock;

import net.nicken.model.Meal;
import net.nicken.repository.MealRepository;
import net.nicken.util.MealsUtil;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;


public class InMemoryMealRepositoryImpl implements MealRepository {
    Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if(meal.isNew())
            meal.setId(counter.incrementAndGet());
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}
