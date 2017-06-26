package net.nicken.web;

import org.junit.Test;

import static net.nicken.MealTestData.MEAL1;
import static net.nicken.MealTestData.MEAL1_ID;
import static net.nicken.UserTestData.USER;
import static net.nicken.model.BaseEntity.START_SEQ;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RootControllerTest extends AbstractControllerTest{

    @Test
    public void testUsers() throws Exception{
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));


    }

    @Test
    public void testMeals() throws Exception{
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(hasProperty("id", is(MEAL1_ID)),
                                hasProperty("description", is(MEAL1.getDescription()))
                        )
                )));
    }
}