package ru.otus.springframework.hw31.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw31.domain.Comment;

import javax.validation.constraints.Min;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByBookInfoId(@Min(1) long bookId, Pageable pageable);
}
