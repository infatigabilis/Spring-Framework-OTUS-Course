package ru.otus.springframework.hw21.repository.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springframework.hw21.domain.BookInfo;
import ru.otus.springframework.hw21.domain.Genre;
import ru.otus.springframework.hw21.repository.AuthorRepository;
import ru.otus.springframework.hw21.repository.BookInfoRepository;
import ru.otus.springframework.hw21.repository.BookInfoRepositoryCustom;
import ru.otus.springframework.hw21.repository.GenreRepository;

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
    public void addAuthorById(long bookId, long authorId) {
        final BookInfo bookInfo = bookInfoRepository.findById(bookId).get();
        bookInfo.getAuthors().add(authorRepository.findById(authorId).get());
        bookInfoRepository.save(bookInfo);
    }

    @Override
    @Transactional
    public void addGenreById(long bookId, long genreId) {
        final Genre genre = genreRepository.findById(genreId).get();
        final BookInfo bookInfo = bookInfoRepository.findById(bookId).get();

        bookInfo.getGenres().add(genre);

        bookInfoRepository.save(bookInfo);
    }

    @Override
    @Transactional
    public void deleteAuthorById(long bookId, long authorId) {
        final BookInfo bookInfo = bookInfoRepository.getOne(bookId);
        bookInfo.getAuthors().remove(authorRepository.getOne(authorId));
        bookInfoRepository.save(bookInfo);
    }

    @Override
    @Transactional
    public void deleteGenreById(long bookId, long genreId) {
        final BookInfo bookInfo = bookInfoRepository.getOne(bookId);
        bookInfo.getGenres().remove(genreRepository.getOne(genreId));
        bookInfoRepository.save(bookInfo);
    }

    @Override
    @Transactional
    public void merge(BookInfo bookInfo) {
        BookInfo actual = bookInfoRepository.getOne(bookInfo.getId());

        actual.setTitle(bookInfo.getTitle());
        actual.setDescription(bookInfo.getDescription());

        bookInfoRepository.save(actual);
    }
}
