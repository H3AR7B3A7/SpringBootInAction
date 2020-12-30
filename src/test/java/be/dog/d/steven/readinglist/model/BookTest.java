package be.dog.d.steven.readinglist.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {
    @Test
    public void testSetId() {
        Book book = new Book();
        book.setId(123L);
        assertEquals(123L, book.getId());
    }

    @Test
    public void testSetReader() {
        Book book = new Book();
        book.setReader("Reader");
        assertEquals("Reader", book.getReader());
    }

    @Test
    public void testSetIsbn() {
        Book book = new Book();
        book.setIsbn("Isbn");
        assertEquals("Isbn", book.getIsbn());
    }

    @Test
    public void testSetTitle() {
        Book book = new Book();
        book.setTitle("Dr");
        assertEquals("Dr", book.getTitle());
    }

    @Test
    public void testSetAuthor() {
        Book book = new Book();
        book.setAuthor("JaneDoe");
        assertEquals("JaneDoe", book.getAuthor());
    }

    @Test
    public void testSetDescription() {
        Book book = new Book();
        book.setDescription("The characteristics of someone or something");
        assertEquals("The characteristics of someone or something", book.getDescription());
    }
}

