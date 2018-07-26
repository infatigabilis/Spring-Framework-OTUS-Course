package ru.otus.springframework.hw08.db.seeder;

import org.springframework.stereotype.Component;
import ru.otus.springframework.hw08.domain.Author;
import ru.otus.springframework.hw08.repository.AuthorRepository;

@Component
public class AuthorSeeder {
    private final AuthorRepository repository;

    public AuthorSeeder(AuthorRepository repository) {
        this.repository = repository;

        seed();
    }

    private void seed() {
        repository.add(new Author(null, "Михаил", "Булгаков"));
        repository.add(new Author(null, "Лев", "Толстой"));
        repository.add(new Author(null, "Richard", "Feynman"));
        repository.add(new Author(null, "Robert", "Leighton"));
        repository.add(new Author(null, "Matthew", "Sands"));
        repository.add(new Author(null, "Michael", "Goodrich"));
        repository.add(new Author(null, "Roberto", "Tamassia"));
    }
}
