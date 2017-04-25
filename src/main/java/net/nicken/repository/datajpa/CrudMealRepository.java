package net.nicken.repository.datajpa;

import net.nicken.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudMealRepository extends JpaRepository<Meal, Integer>{
}
