package net.nicken.service.datajpa;

import net.nicken.MealTestData;
import net.nicken.model.User;
import net.nicken.service.AbstractJpaUserServiceTest;
import net.nicken.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static net.nicken.Profiles.DATAJPA;
import static net.nicken.UserTestData.MATCHER;
import static net.nicken.UserTestData.USER;
import static net.nicken.UserTestData.USER_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractJpaUserServiceTest{

    @Test
    public void testGetWithMeals() throws Exception{
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, user.getMeals());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithMealsNotFound() throws Exception{
        service.getWithMeals(1);
    }

}
