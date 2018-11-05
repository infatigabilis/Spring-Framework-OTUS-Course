package ru.otus.springframework.hw29.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw29.domain.Comment;

import javax.validation.constraints.Min;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    Page<Comment> findByBookInfoId(@Min(1) long bookId, Pageable pageable);
}
