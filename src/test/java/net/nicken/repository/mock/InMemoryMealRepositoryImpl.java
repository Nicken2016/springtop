package net.nicken.repository.mock;

import net.nicken.model.Meal;
import net.nicken.repository.MealRepository;
import net.nicken.util.DateTimeUtil;
import net.nicken.util.MealsUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.nicken.UserTestData.ADMIN_ID;
import static net.nicken.UserTestData.USER_ID;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(um -> save(um, USER_ID));

        save(new Meal(LocalDateTime.of(2017, Month.JANUARY, 20, 12, 0), "Админ ланч", 510), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2017, Month.JANUARY, 20, 21, 0), "Админ ужин", 1500), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal);
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if(meal.isNew()){
            meal.setId(counter.incrementAndGet());
        }else if (get(meal.getId(), userId) == null){
            return null;
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAllAsStream(userId).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);

        return getAllAsStream(userId)
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }

    private Stream<Meal> getAllAsStream(int userId){
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ?
                Stream.empty() : meals.values().stream().sorted(MEAL_COMPARATOR);
    }
}