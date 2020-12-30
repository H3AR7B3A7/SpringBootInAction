package be.dog.d.steven.readinglist.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReaderTest {
    @Test
    public void testSetUsername() {
        Reader reader = new Reader();
        reader.setUsername("janedoe");
        assertEquals("janedoe", reader.getUsername());
    }

    @Test
    public void testSetFullname() {
        Reader reader = new Reader();
        reader.setFullname("Dr Jane Doe");
        assertEquals("Dr Jane Doe", reader.getFullname());
    }

    @Test
    public void testSetPassword() {
        Reader reader = new Reader();
        reader.setPassword("iloveyou");
        assertEquals("iloveyou", reader.getPassword());
    }

    @Test
    public void testGetAuthorities() {
        assertEquals(1, (new Reader()).getAuthorities().size());
    }
}

