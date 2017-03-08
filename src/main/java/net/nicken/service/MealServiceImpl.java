package net.nicken.service;

import net.nicken.model.Meal;
import net.nicken.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Meal get(int id) {
        return null;
    }

    @Override
    public Meal getByEmail(String email) {
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return null;
    }
}
