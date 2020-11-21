package be.dog.d.steven.readinglist.dao;

import be.dog.d.steven.readinglist.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListDao extends JpaRepository<Book, Long> {

List<Book> findByReader(String reader);
}
