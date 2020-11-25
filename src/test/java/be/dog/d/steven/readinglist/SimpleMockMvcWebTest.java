package be.dog.d.steven.readinglist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleMockMvcWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "test") // userDetailsServiceBeanName = "myUserDetailsService"
    void homePageForExistingUser() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books",
                        hasSize(3)));
    }

    @Test
    @WithMockUser(value="unknown")
    void rejectingUsersWithoutRoles() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isForbidden());
    }
}
