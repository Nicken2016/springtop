package net.nicken;

import net.nicken.to.MealWithExceed;
import net.nicken.model.Role;
import net.nicken.model.User;
import net.nicken.web.meal.MealRestController;
import net.nicken.web.user.AdminRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {

    public static void main(String[] args) {
        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")){
            System.out.println("Bean definition names: "+Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            System.out.println("--");

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceed =
                    mealController.getBetween(
                            LocalDate.of(2017, Month.JANUARY, 20), LocalTime.of(7, 0),
                            LocalDate.of(2017, Month.JANUARY, 22), LocalTime.of(22, 0)
                    );
            filteredMealsWithExceed.forEach(System.out::println);
            System.out.println("--");
        }
    }




}
