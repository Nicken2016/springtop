package net.nicken.service.datajpa;

import net.nicken.UserTestData;
import net.nicken.model.Meal;
import net.nicken.service.AbstractMealServiceTest;
import net.nicken.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static net.nicken.MealTestData.*;
import static net.nicken.Profiles.DATAJPA;
import static net.nicken.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest{
    @Test
    public void testGetWithUser() throws Exception{
        Meal adminMeal =  service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, adminMeal);
        UserTestData.MATCHER.assertEquals(UserTestData.ADMIN, adminMeal.getUser());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithUserNotFound() throws Exception{
        service.getWithUser(MEAL1_ID, ADMIN_ID);
    }

}
