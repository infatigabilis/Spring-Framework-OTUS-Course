package ru.otus.springframework.hw08.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springframework.hw08.domain.Author;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class AuthorDaoTest {
    @Autowired private AuthorRepository dao;

    @Test
    public void getAll() {
        List<Author> actual = dao.getAll();

        assertEquals(7, actual.size());
        assertTrue(actual.stream().anyMatch(a -> a.getName().equals("Лев")));
        assertTrue(actual.stream().anyMatch(a -> a.getName().equals("Richard")));
        assertTrue(actual.stream().anyMatch(a -> a.getSurname().equals("Sands")));
    }

    @Test
    public void insert() {
        dao.add(new Author(0, "Hello", null));

        List<Author> actual = dao.getAll();

        assertEquals(8, actual.size());
        assertTrue(actual.stream().anyMatch(a -> a.getName().equals("Hello")));
    }
}
