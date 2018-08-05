package ru.otus.springframework.hw10.db;

import org.springframework.stereotype.Component;
import ru.otus.springframework.hw10.domain.Author;
import ru.otus.springframework.hw10.domain.BookInfo;
import ru.otus.springframework.hw10.domain.Comment;
import ru.otus.springframework.hw10.domain.Genre;
import ru.otus.springframework.hw10.repository.AuthorRepository;
import ru.otus.springframework.hw10.repository.BookInfoRepository;
import ru.otus.springframework.hw10.repository.CommentRepository;
import ru.otus.springframework.hw10.repository.GenreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;

@Component
public class Seeder {
    private final BookInfoRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public Seeder(BookInfoRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;

        run();
    }

    private void run() {
        seedAuthors();
        seedGenres();
        seedBooks();
        seedComments();
    }


    private void seedAuthors() {
        authorRepository.save(new Author(null, "Михаил", "Булгаков"));
        authorRepository.save(new Author(null, "Лев", "Толстой"));
        authorRepository.save(new Author(null, "Richard", "Feynman"));
        authorRepository.save(new Author(null, "Robert", "Leighton"));
        authorRepository.save(new Author(null, "Matthew", "Sands"));
        authorRepository.save(new Author(null, "Michael", "Goodrich"));
        authorRepository.save(new Author(null, "Roberto", "Tamassia"));
    }

    private void seedGenres() {
        genreRepository.save(new Genre(null, "Роман"));
        genreRepository.save(new Genre(null, "Классика"));
        genreRepository.save(new Genre(null, "Мистика"));
        genreRepository.save(new Genre(null, "Сатира"));
        genreRepository.save(new Genre(null, "Java"));
        genreRepository.save(new Genre(null, "Учебник"));
        genreRepository.save(new Genre(null, "Documentation"));
        genreRepository.save(new Genre(null, "Физика"));
    }

    private void seedBooks() {

        bookRepository.save(new BookInfo(
                null,
                "Мастер и Маргарита",
                "Обещание, содержащееся на страницах книги - \"ваш роман вам принесет еще сюрпризы\", - оправдалось вполне: написанный Мастером провидческий роман о дьяволе, пожалуй, явился одной из самых загадочных, удивительных и самых читаемых книг XX столетия! Многие слова и выражения из этого произведения вошли в современный лексикон, а персонажи своею реальностью затмили действительно существующих граждан.",
                Collections.singleton(authorRepository.findById(1L).get()),
                new LinkedHashSet<>(Arrays.asList(genreRepository.findById(1L).get(), genreRepository.findById(2L).get(), genreRepository.findById(3L).get(), genreRepository.findById(4L).get())),
                Collections.emptyList()
        ));

        bookRepository.save(new BookInfo(
                null,
                "Война и мир",
                "Роман-эпопея, описывающий события войн против Наполеона: 1805 года и отечественной 1812 года. Признан критикой всего мира величайшим эпическим произведением литературы нового времени.",
                Collections.singleton(authorRepository.findById(2L).get()),
                new LinkedHashSet<>(Arrays.asList(genreRepository.findById(1L).get(), genreRepository.findById(2L).get())),
                Collections.emptyList()
        ));

        bookRepository.save(new BookInfo(
                null,
                "Фейнмановские лекции по физике. Выпуск 9. Квантовая механика",
                null,
                new LinkedHashSet<>(Arrays.asList(authorRepository.findById(3L).get(), authorRepository.findById(4L).get(), authorRepository.findById(5L).get())),
                new LinkedHashSet<>(Arrays.asList(genreRepository.findById(6L).get(), genreRepository.findById(8L).get())),
                Collections.emptyList()
        ));

        bookRepository.save(new BookInfo(
                null,
                "Data Structures and Algorithms in Java",
                "Now revised to reflect the innovations of Java 5.0, Goodrich and Tamassia''s Fourth Edition of Data Structures and Algorithms in Java continues to offer accessible coverage of fundamental data structures, using a consistent object-oriented framework. The authors provide intuition, description, and analysis of fundamental data structures and algorithms. Numerous illustrations, web-based animations, and simplified mathematical analyses justify important analytical concepts.",
                new LinkedHashSet<>(Arrays.asList(authorRepository.findById(6L).get(), authorRepository.findById(7L).get())),
                new LinkedHashSet<>(Arrays.asList(genreRepository.findById(5L).get(), genreRepository.findById(6L).get(), genreRepository.findById(7L).get())),
                Collections.emptyList()
        ));

        bookRepository.save(new BookInfo(
                null,
                "Spring Framework Documentation",
                "The reference documentation is divided into several sections: Core (IoC container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP) Testing (Mock objects, TestContext framework, Spring MVC Test, WebTestClient) Data Access (Transactions, DAO support, JDBC, ORM, Marshalling XML) Web Servlet (Spring MVC, WebSocket, SockJS, STOMP messaging) Web Reactive (Spring WebFlux, WebClient, WebSocket) Integration (Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Cache) Languages (Kotlin, Groovy, Dynamic languages).",
                null,
                null,
                Collections.emptyList()
        ));
    }

    private void seedComments() {
        commentRepository.addWithBookById(Comment.builder().text("Aliquam eros leo, bibendum ac sem et").build(), 1);
        commentRepository.addWithBookById(Comment.builder().text("tempor cursus ante").build(), 1);
        commentRepository.addWithBookById(Comment.builder().text("Mauris erat quam, dapibus in massa vel, commodo pretium odio.").build(), 1);
        commentRepository.addWithBookById(Comment.builder().text("Praesent efficitur et arcu at scelerisque.").build(), 2);
        commentRepository.addWithBookById(Comment.builder().text("Nulla ac efficitur sapien").build(), 3);
        commentRepository.addWithBookById(Comment.builder().text("Mauris sit amet molestie est, et lacinia sem. In hac habitasse platea dictumst. Morbi ex metus").build(), 5);
        commentRepository.addWithBookById(Comment.builder().text("blandit vitae metus a, condimentum consectetur erat").build(), 5);
    }
}
