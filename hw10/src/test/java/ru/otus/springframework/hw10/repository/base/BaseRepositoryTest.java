package ru.otus.springframework.hw10.repository.base;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw10.BaseTest;
import ru.otus.springframework.hw10.repository.AuthorRepository;
import ru.otus.springframework.hw10.repository.BookInfoRepository;
import ru.otus.springframework.hw10.repository.CommentRepository;
import ru.otus.springframework.hw10.repository.GenreRepository;

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
