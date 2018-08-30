package ru.otus.springframework.hw12.repository.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springframework.hw12.domain.BookInfo;
import ru.otus.springframework.hw12.domain.Genre;
import ru.otus.springframework.hw12.repository.AuthorRepository;
import ru.otus.springframework.hw12.repository.BookInfoRepository;
import ru.otus.springframework.hw12.repository.BookInfoRepositoryCustom;
import ru.otus.springframework.hw12.repository.GenreRepository;

public class BookInfoRepositoryCustomImpl implements BookInfoRepositoryCustom {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookInfoRepository bookInfoRepository;

    public BookInfoRepositoryCustomImpl(AuthorRepository authorRepository,
                                        GenreRepository genreRepository,
                                        @Lazy BookInfoRepository bookInfoRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookInfoRepository = bookInfoRepository;
    }

    @Override
    @Transactional
    public void addAuthorById(String bookId, String authorId) {
        final BookInfo bookInfo = bookInfoRepository.findById(bookId).get();
        bookInfo.getAuthors().add(authorRepository.findById(authorId).get());
        bookInfoRepository.save(bookInfo);
    }

    @Override
    @Transactional
    public void addGenreById(String bookId, String genreId) {
        final Genre genre = genreRepository.findById(genreId).get();
        final BookInfo bookInfo = bookInfoRepository.findById(bookId).get();

        bookInfo.getGenres().add(genre);

        bookInfoRepository.save(bookInfo);
    }
}
