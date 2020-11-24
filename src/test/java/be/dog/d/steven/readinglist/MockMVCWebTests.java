package be.dog.d.steven.readinglist;

import be.dog.d.steven.readinglist.security.Reader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MockMVCWebTests {

    @Autowired
    private WebApplicationContext context;

    private static final Logger LOG = Logger.getLogger(MockMVCWebTests.class.getName());
    private MockMvc mockMvc;

    @BeforeEach
    void setMockMvc() {
        LOG.info("!!! Building mock MVC");
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        System.out.println("Mock MVC built!");
    }

    @AfterEach
    void cleanUp(){
        LOG.info("!!! Ending test");
        System.out.println("Cleaning up...");
    }

    @Test
    void test(){
        LOG.info("!!! Test starting");
        System.out.println("Testing...");
    }

    @Test
    public void security() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location",
                        "http://localhost/login"));
    }

    @Test
    @WithUserDetails(value = "test") // userDetailsServiceBeanName = "myUserDetailsService"
    public void homePage() throws Exception {

        Reader reader = new Reader();
        reader.setUsername("test");
        reader.setPassword("test");
        reader.setFullname("test");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books",
                        hasSize(3)));
    }


}
