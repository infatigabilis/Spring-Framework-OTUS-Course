package ru.otus.springframework.hw17;

import org.junit.Test;
import ru.otus.springframework.hw17.base.BaseRepositoryTest;
import ru.otus.springframework.hw17.domain.BookInfo;
import ru.otus.springframework.hw17.domain.Comment;
import ru.otus.springframework.hw17.persistence.CommentDocument;

import static org.junit.Assert.assertEquals;

public class CommentRepositoryTest extends BaseRepositoryTest {

    @Test
    public void whenFindById_thenReturn() {
        final BookInfo book = bookInfoRepository.persist(BookInfo.builder().title("Title").description("Desc").build()).block();

        Comment expected = commentRepository.addWithBookById(Comment.builder().text("Aliquam eros leo, bibendum ac sem et").build(), book.getId()).block();

        CommentDocument actualDoc = commentRepository.findById(expected.getId()).block();
        Comment actual = Comment.builder()
                .id(actualDoc.getId())
                .text(actualDoc.getText())
                .createAt(actualDoc.getCreateAt())
                .bookInfo(bookInfoRepository.getOne(actualDoc.getBookInfoId()).block())
                .build();

        assertEquals(expected.getText(), actual.getText());
        assertEquals(book.getTitle(), actual.getBookInfo().getTitle());
    }
}
