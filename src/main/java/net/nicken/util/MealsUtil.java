package net.nicken.util;

import net.nicken.model.Meal;
import net.nicken.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 20, 7, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 20, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 20, 20, 0), "Ужин", 1000),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 21, 7, 0), "Завтрак", 1500),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 21, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 21, 20, 0), "Ужин", 1000),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 22, 7, 0), "Завтрак", 1500),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 22, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2017, Month.JANUARY, 22, 20, 0), "Ужин", 1500)
    );



    public static void main(String[] args) {
//        MEALS.forEach(meal -> System.out.println(meal));
        getWithExceeded(MEALS, DEFAULT_CALORIES_PER_DAY).forEach(m -> System.out.println(m));
    }

    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int calories){
        return getFilteredWithExceed(meals, LocalTime.MIN, LocalTime.MAX, calories);
    }

    public static List<MealWithExceed> getFilteredWithExceed(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int calories){
        Map<LocalDate, Integer> map = meals.stream().collect(
                Collectors.groupingBy(m -> m.getDate(), Collectors.summingInt(m -> m.getCalories()))
        );

        return meals.stream()
                .filter(m -> DateTimeUtil.isBetweenTime(m.getTime(), startTime, endTime))
                .map(m -> createMealWithExceed(m, map.get(m.getDate()) > calories))
                .collect(Collectors.toList());
    }


    private static MealWithExceed createMealWithExceed(Meal meal, boolean exceed){
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
    }

    private static Collection<MealWithExceed> getFilteredWithExceedByCycle(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int calories){
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        Collection<MealWithExceed> mealWithExceeds = new ArrayList<>();

        meals.forEach(meal -> {
            if(DateTimeUtil.isBetweenTime(meal.getTime(), startTime, endTime)){
                mealWithExceeds.add(createMealWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > calories));
            }
        });
        return mealWithExceeds;
    }



}
