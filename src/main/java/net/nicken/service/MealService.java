package net.nicken.service;

import net.nicken.model.Meal;

import java.util.List;

public interface MealService {

    //    false if not found
    boolean delete(int id);

    //    null if not found
    Meal get(int id);

    //    null if not found
    Meal getByEmail(String email);

    List<Meal> getAll();

}
