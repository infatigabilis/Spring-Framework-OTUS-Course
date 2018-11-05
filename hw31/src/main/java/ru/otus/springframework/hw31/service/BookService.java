package ru.otus.springframework.hw31.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw31.domain.base.ValidationGroup;
import ru.otus.springframework.hw31.domain.BookInfo;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;
import ru.otus.springframework.hw31.repository.BookInfoRepository;

import javax.validation.groups.Default;
import java.util.List;

@Service
public class BookService {
    private final BookInfoRepository bookInfoRepository;
    private final AuthorizeService authorizeService;

    public BookService(BookInfoRepository bookInfoRepository, AuthorizeService authorizeService) {
        this.bookInfoRepository = bookInfoRepository;
        this.authorizeService = authorizeService;
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    @Transactional(readOnly = true)
    public List<BookInfo> getAll() {
        return bookInfoRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "getFallback")
    @Transactional(readOnly = true)
    public BookInfo get(long bookId) {
        return bookInfoRepository.findById(bookId).orElse(null);
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    @Transactional
    public void add(@Validated({Default.class, ValidationGroup.Insert.class}) BookInfo bookInfo, Authentication authentication) {
        authorizeService.createAcl(bookInfoRepository.save(bookInfo), authentication);
    }

    @HystrixCommand(fallbackMethod = "updateFallback")
    @Transactional
    public void update(@Validated({Default.class, ValidationGroup.Update.class}) BookInfo bookInfo) {
        BookInfo actual = bookInfoRepository.getOne(bookInfo.getId());

        actual.setTitle(bookInfo.getTitle());
        actual.setDescription(bookInfo.getDescription());

        bookInfoRepository.save(actual);
    }

    @HystrixCommand(fallbackMethod = "removeFallback")
    public void remove(long bookId) {
        bookInfoRepository.deleteById(bookId);
    }


    private List<BookInfo> getAllFallback(Throwable cause) {
        throw new ServiceUnavailableException("Book DB", cause);
    }

    private BookInfo getFallback(long bookId, Throwable cause) {
        throw new ServiceUnavailableException("Book DB", cause);
    }

    private void addFallback(BookInfo bookInfo, Authentication authentication, Throwable cause) {
        throw new ServiceUnavailableException("Book DB", cause);
    }

    private void updateFallback(BookInfo bookInfo, Throwable cause) {
        throw new ServiceUnavailableException("Book DB", cause);
    }

    private void removeFallback(long bookId, Throwable cause) {
        throw new ServiceUnavailableException("Book DB", cause);
    }
}
