package net.nicken.web.user;

import net.nicken.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static net.nicken.UserTestData.ADMIN_ID;


public class AdminRestControllerTest extends AbstractControllerTest{

    public static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception{
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}