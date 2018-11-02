package ru.otus.springframework.hw31.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw31.domain.base.ValidationGroup;
import ru.otus.springframework.hw31.domain.Author;
import ru.otus.springframework.hw31.domain.BookInfo;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;
import ru.otus.springframework.hw31.repository.AuthorRepository;
import ru.otus.springframework.hw31.repository.BookInfoRepository;

import javax.validation.groups.Default;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookInfoRepository bookInfoRepository;

    public AuthorService(AuthorRepository authorRepository, BookInfoRepository bookInfoRepository) {
        this.authorRepository = authorRepository;
        this.bookInfoRepository = bookInfoRepository;
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "getFallback")
    @Transactional(readOnly = true)
    public Author get(long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public void add(@Validated({Default.class, ValidationGroup.Insert.class}) Author author) {
        authorRepository.save(author);
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public void update(@Validated({Default.class, ValidationGroup.Update.class}) Author author) {
        authorRepository.save(author);
    }

    @HystrixCommand(fallbackMethod = "removeFallback")
    public void remove(long authorId) {
        authorRepository.deleteById(authorId);
    }

    @HystrixCommand(fallbackMethod = "bindFallback")
    @Transactional
    public void bindToBook(long authorId, long bookId) {
        final BookInfo bookInfo = bookInfoRepository.findById(bookId).get();
        bookInfo.getAuthors().add(authorRepository.findById(authorId).get());
        bookInfoRepository.save(bookInfo);
    }


    private List<Author> getAllFallback(Throwable cause) {
        throw new ServiceUnavailableException("Author DB", cause);
    }

    private Author getFallback(long authorId, Throwable cause) {
        throw new ServiceUnavailableException("Author DB", cause);
    }

    private void addFallback(Author author, Throwable cause) {
        throw new ServiceUnavailableException("Author DB", cause);
    }

    private void removeFallback(long authorId, Throwable cause) {
        throw new ServiceUnavailableException("Author DB", cause);
    }

    private void bindFallback(long authorId, long bookId, Throwable cause) {
        throw new ServiceUnavailableException("Author DB", cause);
    }
}
