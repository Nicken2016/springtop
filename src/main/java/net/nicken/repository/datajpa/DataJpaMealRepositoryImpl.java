package net.nicken.repository.datajpa;

import net.nicken.model.Meal;
import net.nicken.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository{

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null){
            return null;
        }

        meal.setUser(crudUserRepository.getOne(userId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudMealRepository.findOne(id);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return crudMealRepository.getAll(userId);
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.getBetween(startDate, endDate, userId);
    }
}
