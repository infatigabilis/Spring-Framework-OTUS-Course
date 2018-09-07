package ru.otus.springframework.hw19.repository;

import org.junit.After;
import org.junit.Test;
import ru.otus.springframework.hw19.domain.BookInfo;
import ru.otus.springframework.hw19.domain.Comment;
import ru.otus.springframework.hw19.repository.base.BaseRepositoryTest;

import static org.junit.Assert.assertEquals;

public class CommentRepositoryTest extends BaseRepositoryTest {

    @After
    public void tearDown() {
        deleteAll();
    }

    @Test
    public void whenFindById_thenReturn() {
        final BookInfo book = BookInfo.builder().title("Title").description("Desc").build();
        bookInfoRepository.save(book);

        Comment expected = Comment.builder().text("Aliquam eros leo, bibendum ac sem et").build();

        commentRepository.addWithBookById(expected, book.getId());

        Comment actual = commentRepository.getOne(expected.getId());

        assertEquals(expected.getText(), actual.getText());
        assertEquals(book.getTitle(), actual.getBookInfo().getTitle());
    }
}
