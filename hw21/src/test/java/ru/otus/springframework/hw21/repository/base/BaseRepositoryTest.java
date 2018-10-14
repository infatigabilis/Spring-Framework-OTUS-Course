package ru.otus.springframework.hw21.repository.base;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw21.BaseTest;
import ru.otus.springframework.hw21.repository.AuthorRepository;
import ru.otus.springframework.hw21.repository.BookInfoRepository;
import ru.otus.springframework.hw21.repository.CommentRepository;
import ru.otus.springframework.hw21.repository.GenreRepository;

public abstract class BaseRepositoryTest extends BaseTest {
    @Autowired protected AuthorRepository authorRepository;
    @Autowired protected GenreRepository genreRepository;
    @Autowired protected BookInfoRepository bookInfoRepository;
    @Autowired protected CommentRepository commentRepository;


    protected void deleteAll() {
        commentRepository.deleteAll();
        bookInfoRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();
    }
}
