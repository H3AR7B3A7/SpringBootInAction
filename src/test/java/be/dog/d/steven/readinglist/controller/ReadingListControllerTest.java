package be.dog.d.steven.readinglist.controller;

import be.dog.d.steven.readinglist.configuration.AmazonProperties;
import be.dog.d.steven.readinglist.dao.ReadingListDao;
import be.dog.d.steven.readinglist.security.Reader;
import be.dog.d.steven.readinglist.security.ReaderDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;
import java.util.Collections;

@WebMvcTest({ReadingListController.class})
@AutoConfigureMockMvc
class ReadingListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AmazonProperties amazonProperties;

    @MockBean
    ReadingListDao readingListDao;

    @MockBean
    ReaderDao readerDao;

    @MockBean
    UserDetailsService myUserDetailsService;

    @Test
    @WithUserDetails(value = "test")
    void test() throws Exception {

        Reader reader= new Reader();
        reader.setUsername("test");
        reader.setPassword("test");
        reader.setFullname("test");

        Mockito.when(readingListDao.findAll()).thenReturn(
                Collections.emptyList()
        );

        Mockito.when(amazonProperties.getAssociateId()).thenReturn("");

        Mockito.when(readerDao.findByUsername("test")).thenReturn(
                java.util.Optional.of(reader)
        );

        // Principal ??? :'(

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_READER"));
            }

            @Override
            public String getPassword() {
                return "test";
            }

            @Override
            public String getUsername() {
                return "test";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        Mockito.when(myUserDetailsService.loadUserByUsername("test")).thenReturn(
                userDetails
        );

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/")
                .accept(MediaType.TEXT_HTML)
        ).andReturn();

        System.out.println(mvcResult.getResponse());

        Mockito.verify(readingListDao).findAll();

    }
}