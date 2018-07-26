package ru.otus.springframework.hw08.repository.base;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import ru.otus.springframework.hw08.BaseTest;
import ru.otus.springframework.hw08.db.seeder.AuthorSeeder;
import ru.otus.springframework.hw08.db.seeder.BookInfoSeeder;
import ru.otus.springframework.hw08.db.seeder.GenreSeeder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseRepositoryTest extends BaseTest {
    @PersistenceContext protected EntityManager em;
    @Autowired protected TransactionTemplate transactionTemplate;
}
