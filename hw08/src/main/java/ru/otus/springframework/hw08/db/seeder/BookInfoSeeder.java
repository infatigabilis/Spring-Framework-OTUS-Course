package ru.otus.springframework.hw08.db.seeder;

import org.springframework.stereotype.Component;
import ru.otus.springframework.hw08.domain.BookInfo;
import ru.otus.springframework.hw08.repository.AuthorRepository;
import ru.otus.springframework.hw08.repository.BookInfoRepository;
import ru.otus.springframework.hw08.repository.GenreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;

@Component
public class BookInfoSeeder {
    private final BookInfoRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookInfoSeeder(BookInfoRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;

        seed();
    }

    private void seed() {

        bookRepository.add(new BookInfo(
                null,
                "Мастер и Маргарита",
                "Обещание, содержащееся на страницах книги - \"ваш роман вам принесет еще сюрпризы\", - оправдалось вполне: написанный Мастером провидческий роман о дьяволе, пожалуй, явился одной из самых загадочных, удивительных и самых читаемых книг XX столетия! Многие слова и выражения из этого произведения вошли в современный лексикон, а персонажи своею реальностью затмили действительно существующих граждан.",
                Collections.singleton(authorRepository.getById(1)),
                new LinkedHashSet<>(Arrays.asList(genreRepository.getById(1), genreRepository.getById(2), genreRepository.getById(3), genreRepository.getById(4))),
                Collections.emptyList()
        ));

        bookRepository.add(new BookInfo(
                null,
                "Война и мир",
                "Роман-эпопея, описывающий события войн против Наполеона: 1805 года и отечественной 1812 года. Признан критикой всего мира величайшим эпическим произведением литературы нового времени.",
                Collections.singleton(authorRepository.getById(2)),
                new LinkedHashSet<>(Arrays.asList(genreRepository.getById(1), genreRepository.getById(2))),
                Collections.emptyList()
        ));

        bookRepository.add(new BookInfo(
                null,
                "Фейнмановские лекции по физике. Выпуск 9. Квантовая механика",
                null,
                new LinkedHashSet<>(Arrays.asList(authorRepository.getById(3), authorRepository.getById(4), authorRepository.getById(5))),
                new LinkedHashSet<>(Arrays.asList(genreRepository.getById(6), genreRepository.getById(8))),
                Collections.emptyList()
        ));

        bookRepository.add(new BookInfo(
                null,
                "Data Structures and Algorithms in Java",
                "Now revised to reflect the innovations of Java 5.0, Goodrich and Tamassia''s Fourth Edition of Data Structures and Algorithms in Java continues to offer accessible coverage of fundamental data structures, using a consistent object-oriented framework. The authors provide intuition, description, and analysis of fundamental data structures and algorithms. Numerous illustrations, web-based animations, and simplified mathematical analyses justify important analytical concepts.",
                new LinkedHashSet<>(Arrays.asList(authorRepository.getById(6), authorRepository.getById(7))),
                new LinkedHashSet<>(Arrays.asList(genreRepository.getById(5), genreRepository.getById(6), genreRepository.getById(7))),
                Collections.emptyList()
        ));

        bookRepository.add(new BookInfo(
                null,
                "Spring Framework Documentation",
                "The reference documentation is divided into several sections: Core (IoC container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP) Testing (Mock objects, TestContext framework, Spring MVC Test, WebTestClient) Data Access (Transactions, DAO support, JDBC, ORM, Marshalling XML) Web Servlet (Spring MVC, WebSocket, SockJS, STOMP messaging) Web Reactive (Spring WebFlux, WebClient, WebSocket) Integration (Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Cache) Languages (Kotlin, Groovy, Dynamic languages).",
                null,
                null,
                Collections.emptyList()
        ));
    }
}
