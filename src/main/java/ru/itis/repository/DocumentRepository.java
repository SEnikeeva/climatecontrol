package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.Document;

@Repository
@Transactional
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
