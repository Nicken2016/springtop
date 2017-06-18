package net.nicken.web.json;

import net.nicken.MealTestData;
import net.nicken.model.Meal;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JsonUtilTest {
    @Test
    public void testReadWriteValue() throws Exception{
        String json = JsonUtil.writeValue(MealTestData.ADMIN_MEAL1);
        System.out.println(json);
        Meal userMeal = JsonUtil.readValue(json, Meal.class);
        MealTestData.MATCHER.assertEquals(MealTestData.ADMIN_MEAL1, userMeal);
    }

    @Test
    public void testReadWriteValues() throws Exception{
        String json = JsonUtil.writeValue(MealTestData.MEALS);
        System.out.println(json);
        List<Meal> userMeals = JsonUtil.readValues(json, Meal.class);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, userMeals);
    }
}