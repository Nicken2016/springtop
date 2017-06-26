package net.nicken.web.meal;

import net.nicken.AuthorizedUser;
import net.nicken.model.Meal;
import net.nicken.service.MealService;
import net.nicken.to.MealWithExceed;
import net.nicken.util.DateTimeUtil;
import net.nicken.util.MealsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static net.nicken.util.ValidationUtil.checkIdConsistent;
import static net.nicken.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
private static final Logger LOG = LoggerFactory.getLogger(AbstractMealController.class);

@Autowired
private MealService service;

public Meal get(int id){
    int userId = AuthorizedUser.id();
    LOG.info("get meal {} for User {}", id, userId);
    return service.get(id, userId);
}

public void delete(int id){
    int userId = AuthorizedUser.id();
    LOG.info("delete meal {} for User {}", id, userId);
    service.delete(id, userId);
}

public List<MealWithExceed> getAll(){
    int userId = AuthorizedUser.id();
    LOG.info("get All for User {}", userId);
    return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
}

public void update(Meal meal, int id){
    checkIdConsistent(meal, id);
    int userId = AuthorizedUser.id();
    LOG.info("update {} for User {}", meal, userId);
    service.update(meal, userId);
}

public Meal create(Meal meal){
    checkNew(meal);
    int userId = AuthorizedUser.id();
    LOG.info("create {} for User{}", meal, userId);
    return service.save(meal, userId);
}

public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
    int userId = AuthorizedUser.id();
    LOG.info("getNetween dates {} - {} for time {} = {} for User {}", startDate, endDate, startTime, endTime, userId);
    return MealsUtil.getFilteredWithExceed(
            service.getBetweenDates(
                    startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                    endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
            startTime != null ? startTime : LocalTime.MIN,
            endTime != null ? endTime : LocalTime.MAX, AuthorizedUser.getCaloriesPerDay());
  }
}


