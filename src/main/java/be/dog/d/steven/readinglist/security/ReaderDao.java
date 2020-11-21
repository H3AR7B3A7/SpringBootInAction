package be.dog.d.steven.readinglist.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderDao extends JpaRepository<Reader, String> {
}
