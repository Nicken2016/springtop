package net.nicken.service;

import net.nicken.model.Meal;
import net.nicken.util.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;


public interface MealService {

    Meal get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    default Collection<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId){
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    Collection<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    Collection<Meal> getAll(int userId);

    Meal update(Meal meal, int userId) throws NotFoundException;

    Meal save(Meal meal, int userId);
}
