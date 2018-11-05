package ru.otus.springframework.hw27.repository;

import org.junit.After;
import org.junit.Test;
import ru.otus.springframework.hw27.domain.BookInfo;
import ru.otus.springframework.hw27.domain.Comment;
import ru.otus.springframework.hw27.repository.base.BaseRepositoryTest;

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

        Comment expected = new Comment("Aliquam eros leo, bibendum ac sem et", book);

        commentRepository.save(expected);

        Comment actual = commentRepository.getOne(expected.getId());

        assertEquals(expected.getText(), actual.getText());
        assertEquals(book.getTitle(), actual.getBookInfo().getTitle());
    }
}
