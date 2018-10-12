package ru.otus.springframework.hw17.base;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springframework.hw17.db.DataPopulator;
import ru.otus.springframework.hw17.repository.AuthorRepository;
import ru.otus.springframework.hw17.repository.BookInfoRepository;
import ru.otus.springframework.hw17.repository.CommentRepository;
import ru.otus.springframework.hw17.repository.GenreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseRepositoryTest {
    private static boolean init = true;

    @MockBean private DataPopulator seeder;

    @Autowired protected AuthorRepository authorRepository;
    @Autowired protected GenreRepository genreRepository;
    @Autowired protected BookInfoRepository bookInfoRepository;
    @Autowired protected CommentRepository commentRepository;

    @Before
    public void setUp() {
        if (init) {
            deleteAll();
            init = false;
        }
    }

    @After
    public void tearDown() {
        deleteAll();
    }

    private void deleteAll() {
        commentRepository.deleteAll().block();
        bookInfoRepository.deleteAll().block();
        authorRepository.deleteAll().block();
        genreRepository.deleteAll().block();
    }
}
