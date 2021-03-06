package be.dog.d.steven.readinglist;

import be.dog.d.steven.readinglist.dao.ReadingListDao;
import be.dog.d.steven.readinglist.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class MockMvcWebTests {

    @Autowired
    private WebApplicationContext context;

//    @Autowired
//    private ReaderDao dao; // Can't quite figure out how to add 'reader' with roles

    @Autowired
    private ReadingListDao readingListDao;

    private static final Logger LOG = Logger.getLogger(MockMvcWebTests.class.getName());
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
    void emptyTest(){
        LOG.info("!!! Test starting");

        System.out.println("Testing...");
    }

    @Test
    void securityLoginRedirection() throws Exception {
        LOG.info("!!! Testing login redirection");

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location",
                        "http://localhost/login"));
        System.out.println("Redirected to login.");
    }

    @Test
    @WithUserDetails(value = "test") // userDetailsServiceBeanName = "myUserDetailsService"
    void homePageForExistingUser() throws Exception {
        LOG.info("!!! Testing homepage for existing user");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books",
                        hasSize(2)));
        System.out.println("Size matched expected");
    }

    @Test
    @WithUserDetails(value = "test") // userDetailsServiceBeanName = "myUserDetailsService"
    void homePageAfterAddingBookForExistingUser() throws Exception {
        LOG.info("!!! Testing homepage for existing user");

        Book book = new Book();
        book.setTitle("title");
        book.setAuthor("author");
        book.setReader("test");
        book.setIsbn("B001");
        book.setDescription("desc");

        readingListDao.save(book);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books",
                        hasSize(3)));
        System.out.println("Size matched expected");

        readingListDao.delete(book);
    }
}
