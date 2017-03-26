package net.nicken.service;

import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import net.nicken.model.Meal;
import net.nicken.util.DbPopulator;
import net.nicken.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static net.nicken.MealTestData.*;
import static net.nicken.UserTestData.ADMIN_ID;
import static net.nicken.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception{
        dbPopulator.execute();
    }

    @Test
    public void testDelete() throws Exception{
        service.delete(MEAL1_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception{
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void testSave()throws Exception{
        Meal created = getCreated();
        service.save(created, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
    }

    @Test
    public void testGet()throws Exception{
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound(){
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void testUpdate()throws Exception{
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception{
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void testGetAll() throws Exception{
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
                service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID));
    }
}
