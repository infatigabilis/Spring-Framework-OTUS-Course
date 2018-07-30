package ru.otus.springframework.hw08;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springframework.hw08.db.seeder.AuthorSeeder;
import ru.otus.springframework.hw08.db.seeder.BookInfoSeeder;
import ru.otus.springframework.hw08.db.seeder.GenreSeeder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public abstract class BaseTest {
    @MockBean private AuthorSeeder authorSeeder;
    @MockBean private BookInfoSeeder bookInfoSeeder;
    @MockBean private GenreSeeder genreSeeder;
}
