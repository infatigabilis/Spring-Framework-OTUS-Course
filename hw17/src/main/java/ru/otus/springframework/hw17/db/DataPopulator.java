package ru.otus.springframework.hw17.db;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.springframework.hw17.domain.Author;
import ru.otus.springframework.hw17.domain.BookInfo;
import ru.otus.springframework.hw17.domain.Comment;
import ru.otus.springframework.hw17.domain.Genre;
import ru.otus.springframework.hw17.repository.AuthorRepository;
import ru.otus.springframework.hw17.repository.BookInfoRepository;
import ru.otus.springframework.hw17.repository.CommentRepository;
import ru.otus.springframework.hw17.repository.GenreRepository;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DataPopulator {
    private boolean executed = false;

    private final BookInfoRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public DataPopulator(BookInfoRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void populate() {
        if (executed) return;

        commentRepository.deleteAll().block();
        bookRepository.deleteAll().block();
        authorRepository.deleteAll().block();
        genreRepository.deleteAll().block();

        populateAuthors();
        populateGenres();
        populateBooks();
        populateComments();

        executed = true;
    }


    private void populateAuthors() {
        authorRepository.save(new Author(null, "Михаил", "Булгаков")).block();
        authorRepository.save(new Author(null, "Лев", "Толстой")).block();
        authorRepository.save(new Author(null, "Richard", "Feynman")).block();
        authorRepository.save(new Author(null, "Robert", "Leighton")).block();
        authorRepository.save(new Author(null, "Matthew", "Sands")).block();
        authorRepository.save(new Author(null, "Michael", "Goodrich")).block();
        authorRepository.save(new Author(null, "Roberto", "Tamassia")).block();
    }

    private void populateGenres() {
        genreRepository.save(new Genre(null, "Роман")).block();
        genreRepository.save(new Genre(null, "Классика")).block();
        genreRepository.save(new Genre(null, "Мистика")).block();
        genreRepository.save(new Genre(null, "Сатира")).block();
        genreRepository.save(new Genre(null, "Java")).block();
        genreRepository.save(new Genre(null, "Учебник")).block();
        genreRepository.save(new Genre(null, "Documentation")).block();
        genreRepository.save(new Genre(null, "Физика")).block();
    }

    private void populateBooks() {

        bookRepository.persist(new BookInfo(
                null,
                "Мастер и Маргарита",
                "Обещание, содержащееся на страницах книги - \"ваш роман вам принесет еще сюрпризы\", - оправдалось вполне: написанный Мастером провидческий роман о дьяволе, пожалуй, явился одной из самых загадочных, удивительных и самых читаемых книг XX столетия! Многие слова и выражения из этого произведения вошли в современный лексикон, а персонажи своею реальностью затмили действительно существующих граждан.",
                Arrays.asList(authorRepository.findAll().blockFirst()),
                Arrays.asList(genreRepository.findAll().blockFirst(), genreRepository.findAll().skip(1).blockFirst(), genreRepository.findAll().skip(2).blockFirst(), genreRepository.findAll().skip(3).blockFirst())
        )).block();

        bookRepository.persist(new BookInfo(
                null,
                "Война и мир",
                "Роман-эпопея, описывающий события войн против Наполеона: 1805 года и отечественной 1812 года. Признан критикой всего мира величайшим эпическим произведением литературы нового времени.",
                Arrays.asList(authorRepository.findAll().skip(1).blockFirst()),
                Arrays.asList(genreRepository.findAll().skip(0).blockFirst(), genreRepository.findAll().skip(1).blockFirst())
        )).block();

        bookRepository.persist(new BookInfo(
                null,
                "Фейнмановские лекции по физике. Выпуск 9. Квантовая механика",
                "",
                Arrays.asList(authorRepository.findAll().skip(2).blockFirst(), authorRepository.findAll().skip(3).blockFirst(), authorRepository.findAll().skip(4).blockFirst()),
                Arrays.asList(genreRepository.findAll().skip(5).blockFirst(), genreRepository.findAll().skip(7).blockFirst())
        )).block();

        bookRepository.persist(new BookInfo(
                null,
                "Data Structures and Algorithms in Java",
                "Now revised to reflect the innovations of Java 5.0, Goodrich and Tamassia''s Fourth Edition of Data Structures and Algorithms in Java continues to offer accessible coverage of fundamental data structures, using a consistent object-oriented framework. The authors provide intuition, description, and analysis of fundamental data structures and algorithms. Numerous illustrations, web-based animations, and simplified mathematical analyses justify important analytical concepts.",
                Arrays.asList(authorRepository.findAll().skip(5).blockFirst(), authorRepository.findAll().skip(6).blockFirst()),
                Arrays.asList(genreRepository.findAll().skip(4).blockFirst(), genreRepository.findAll().skip(5).blockFirst(), genreRepository.findAll().skip(6).blockFirst())
        )).block();

        bookRepository.persist(new BookInfo(
                null,
                "Spring Framework Documentation",
                "The reference documentation is divided into several sections: Core (IoC container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP) Testing (Mock objects, TestContext framework, Spring MVC Test, WebTestClient) Data Access (Transactions, DAO support, JDBC, ORM, Marshalling XML) Web Servlet (Spring MVC, WebSocket, SockJS, STOMP messaging) Web Reactive (Spring WebFlux, WebClient, WebSocket) Integration (Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Cache) Languages (Kotlin, Groovy, Dynamic languages).",
                Collections.emptyList(),
                Collections.emptyList()
        )).block();
    }

    private void populateComments() {
        commentRepository.addWithBookById(Comment.builder().text("Aliquam eros leo, bibendum ac sem et").build(), bookRepository.findAll().blockFirst().getId()).block();
        commentRepository.addWithBookById(Comment.builder().text("tempor cursus ante").build(), bookRepository.findAll().blockFirst().getId()).block();
        commentRepository.addWithBookById(Comment.builder().text("Mauris erat quam, dapibus in massa vel, commodo pretium odio.").build(), bookRepository.findAll().blockFirst().getId()).block();
        commentRepository.addWithBookById(Comment.builder().text("Praesent efficitur et arcu at scelerisque.").build(), bookRepository.findAll().skip(1).blockFirst().getId()).block();
        commentRepository.addWithBookById(Comment.builder().text("Nulla ac efficitur sapien").build(), bookRepository.findAll().skip(2).blockFirst().getId()).block();
        commentRepository.addWithBookById(Comment.builder().text("Mauris sit amet molestie est, et lacinia sem. In hac habitasse platea dictumst. Morbi ex metus").build(), bookRepository.findAll().skip(4).blockFirst().getId()).block();
        commentRepository.addWithBookById(Comment.builder().text("blandit vitae metus a, condimentum consectetur erat").build(), bookRepository.findAll().skip(4).blockFirst().getId()).block();
    }
}
