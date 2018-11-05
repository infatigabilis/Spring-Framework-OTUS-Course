package ru.otus.springframework.hw31.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw31.domain.base.ValidationGroup;
import ru.otus.springframework.hw31.domain.Author;
import ru.otus.springframework.hw31.domain.BookInfo;
import ru.otus.springframework.hw31.domain.Genre;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;
import ru.otus.springframework.hw31.repository.BookInfoRepository;
import ru.otus.springframework.hw31.repository.GenreRepository;

import javax.validation.groups.Default;
import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookInfoRepository bookInfoRepository;

    public GenreService(GenreRepository genreRepository, BookInfoRepository bookInfoRepository) {
        this.genreRepository = genreRepository;
        this.bookInfoRepository = bookInfoRepository;
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "getFallback")
    @Transactional(readOnly = true)
    public Genre get(long authorId) {
        return genreRepository.findById(authorId).orElse(null);
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public void add(@Validated({Default.class, ValidationGroup.Insert.class}) Genre genre) {
        genreRepository.save(genre);
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public void update(@Validated({Default.class, ValidationGroup.Update.class}) Genre genre) {
        genreRepository.save(genre);
    }

    @HystrixCommand(fallbackMethod = "removeFallback")
    public void remove(long genreId) {
        genreRepository.deleteById(genreId);
    }

    @HystrixCommand(fallbackMethod = "bindFallback")
    @Transactional
    public void bindToBook(long genreId, long bookId) {
        final BookInfo bookInfo = bookInfoRepository.findById(bookId).get();
        bookInfo.getGenres().add(genreRepository.findById(genreId).get());
        bookInfoRepository.save(bookInfo);
    }


    private List<Genre> getAllFallback(Throwable cause) {
        throw new ServiceUnavailableException("Genre DB", cause);
    }

    private Genre getFallback(long genreId, Throwable cause) {
        throw new ServiceUnavailableException("Genre DB", cause);
    }

    private void addFallback(Genre genre, Throwable cause) {
        throw new ServiceUnavailableException("Genre DB", cause);
    }

    private void removeFallback(long genreId, Throwable cause) {
        throw new ServiceUnavailableException("Genre DB", cause);
    }

    private void bindFallback(long genreId, long bookId, Throwable cause) {
        throw new ServiceUnavailableException("Genre DB", cause);
    }
}
