package be.dog.d.steven.readinglist.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderDao extends JpaRepository<Reader, String> {
    Optional<Reader> findByUsername(String username);
}
