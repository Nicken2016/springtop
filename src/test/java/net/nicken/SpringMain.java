package net.nicken;


import net.nicken.to.MealWithExceed;
import net.nicken.web.meal.MealRestController;
import net.nicken.web.user.AdminRestController;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static net.nicken.UserTestData.USER_ID;

public class SpringMain {

    public static void main(String[] args) {

          try(GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()){
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.DB_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: "+Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.get(USER_ID);
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceed =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0)
                    );
            filteredMealsWithExceed.forEach(System.out::println);
            System.out.println("--");
        }
    }




}
