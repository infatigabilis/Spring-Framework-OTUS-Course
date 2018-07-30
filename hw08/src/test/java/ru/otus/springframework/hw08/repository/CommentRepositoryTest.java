package ru.otus.springframework.hw08.repository;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw08.domain.BookInfo;
import ru.otus.springframework.hw08.domain.Comment;
import ru.otus.springframework.hw08.repository.base.BaseRepositoryTest;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CommentRepositoryTest extends BaseRepositoryTest {
    @Autowired private BookInfoRepository bookInfoRepository;
    @Autowired private CommentRepository repository;

    @After
    public void setUp() {
        transactionTemplate.execute(status -> {
            bookInfoRepository.getAll().forEach(book -> {
                book.getComments().forEach(o -> em.remove(o));
                em.remove(book);
            });
            return null;
        });
    }

    @Test
    public void whenFindById_thenReturn() {
        final BookInfo book = new BookInfo(null, "Title", "Desc", Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        bookInfoRepository.add(book);

        Comment comment1 = Comment.builder().text("Aliquam eros leo, bibendum ac sem et").build();
        Comment comment2 = Comment.builder().text("tempor cursus ante").build();

        repository.add(comment1, book.getId());
        repository.add(comment2, book.getId());

        final BookInfo actual = bookInfoRepository.getById(book.getId());

        assertEquals(2, actual.getComments().size());
        assertEquals(comment1.getText(), actual.getComments().get(0).getText());
        assertEquals(comment2.getText(), actual.getComments().get(1).getText());
    }
}
