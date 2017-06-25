package net.nicken.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import net.nicken.TestUtil;
import net.nicken.model.Role;
import net.nicken.model.User;
import net.nicken.web.AbstractControllerTest;
import net.nicken.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static net.nicken.UserTestData.*;
import static net.nicken.web.user.ProfileRestController.REST_URL;
public class ProfileRestControllerTest extends AbstractControllerTest{

    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN), userService.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_ID, "newUser", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, new User(userService.getByEmail("newemail@ya.ru")));

    }

}