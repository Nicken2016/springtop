package net.nicken.web.meal;

import net.nicken.web.LocalExceptionInfoHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = AbstractMealController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MealExceptionInfoHandler extends LocalExceptionInfoHandler{

    public MealExceptionInfoHandler() {
        super("exception.meals.duplicate_datetime");
    }
}
