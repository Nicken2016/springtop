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


    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int calories){
        return getFilteredWithExceed(meals, LocalTime.MIN, LocalTime.MAX, calories);
    }

    public static List<MealWithExceed> getFilteredWithExceed(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int calories){
        Map<LocalDate, Integer> map = meals.stream().collect(
                Collectors.groupingBy(m -> m.getDate(), Collectors.summingInt(m -> m.getCalories()))
        );

        return meals.stream()
                .filter(m -> DateTimeUtil.isBetweenTime(m.getTime(), startTime, endTime))
                .map(m -> createWithExceed(m, map.get(m.getDate()) > calories))
                .collect(Collectors.toList());
    }


    public static MealWithExceed createWithExceed(Meal meal, boolean exceed){
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
    }

    private static Collection<MealWithExceed> getFilteredWithExceedByCycle(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int calories){
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        Collection<MealWithExceed> mealWithExceeds = new ArrayList<>();

        meals.forEach(meal -> {
            if(DateTimeUtil.isBetweenTime(meal.getTime(), startTime, endTime)){
                mealWithExceeds.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > calories));
            }
        });
        return mealWithExceeds;
    }



}
