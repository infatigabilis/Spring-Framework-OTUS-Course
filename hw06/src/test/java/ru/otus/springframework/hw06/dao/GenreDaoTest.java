package ru.otus.springframework.hw06.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springframework.hw06.dao.GenreDao;
import ru.otus.springframework.hw06.domain.Genre;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class GenreDaoTest {
    @Autowired private GenreDao dao;

    @Test
    public void getAll() {
        List<Genre> actual = dao.getAll();

        assertEquals(8, actual.size());
        assertTrue(actual.stream().anyMatch(g -> g.getName().equals("Роман")));
        assertTrue(actual.stream().anyMatch(g -> g.getName().equals("Documentation")));
    }

    @Test
    public void insert() {
        dao.insert(new Genre(0, "Pulp Fiction"));

        List<Genre> actual = dao.getAll();

        assertEquals(9, actual.size());
        assertTrue(actual.stream().anyMatch(g -> g.getName().equals("Pulp Fiction")));
    }
}
