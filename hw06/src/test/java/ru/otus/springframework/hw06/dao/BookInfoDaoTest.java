package ru.otus.springframework.hw06.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springframework.hw06.domain.BookInfo;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookInfoDaoTest {
    @Autowired private BookInfoDao dao;

    @Test
    public void getById() {
        BookInfo actual = dao.getById(4);

        assertEquals("Data Structures and Algorithms in Java", actual.getTitle());
        assertEquals(2, actual.getAuthors().size());
        assertTrue(actual.getAuthors().stream().anyMatch(a -> a.getName().equals("Roberto")));
        assertEquals(3, actual.getGenres().size());
        assertTrue(actual.getGenres().stream().anyMatch(g -> g.getName().equals("Java")));
    }

    @Test
    public void getAll() {
        List<BookInfo> actual = dao.getAll();

        assertEquals(5, actual.size());
        assertTrue(actual.stream().anyMatch(b -> b.getTitle().equals("Война и мир")));
        assertTrue(actual.stream().anyMatch(b -> b.getTitle().equals("Data Structures and Algorithms in Java")));
        assertTrue(actual.stream().anyMatch(b -> b.getTitle().equals("Фейнмановские лекции по физике. Выпуск 9. Квантовая механика")));
    }

    @Test
    public void insert() {
        dao.insert(new BookInfo(0, "Hello World", "123", null, null));

        List<BookInfo> actual = dao.getAll();

        assertEquals(6, actual.size());
        assertTrue(actual.stream().anyMatch(b -> b.getTitle().equals("Hello World")));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testValidation() {
        dao.insert(new BookInfo(-1, "Hello World", "123", null, null));
    }

    @Test
    public void addAuthor() {
        dao.addAuthor(3, 1);

        BookInfo actual = dao.getById(3);

        assertEquals(4, actual.getAuthors().size());
        assertTrue(actual.getAuthors().stream().anyMatch(a -> a.getName().equals("Михаил")));
    }

    @Test
    public void addGenre() {
        dao.addGenre(3, 1);

        BookInfo actual = dao.getById(3);

        assertEquals(3, actual.getGenres().size());
        assertTrue(actual.getGenres().stream().anyMatch(a -> a.getName().equals("Роман")));
    }
}
