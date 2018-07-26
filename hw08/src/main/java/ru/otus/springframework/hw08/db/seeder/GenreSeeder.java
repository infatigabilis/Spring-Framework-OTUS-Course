package ru.otus.springframework.hw08.db.seeder;

import org.springframework.stereotype.Component;
import ru.otus.springframework.hw08.domain.Genre;
import ru.otus.springframework.hw08.repository.GenreRepository;

@Component
public class GenreSeeder {
    private final GenreRepository repository;

    public GenreSeeder(GenreRepository repository) {
        this.repository = repository;

        seed();
    }

    private void seed() {
        repository.add(new Genre(null, "Роман"));
        repository.add(new Genre(null, "Классика"));
        repository.add(new Genre(null, "Мистика"));
        repository.add(new Genre(null, "Сатира"));
        repository.add(new Genre(null, "Java"));
        repository.add(new Genre(null, "Учебник"));
        repository.add(new Genre(null, "Documentation"));
        repository.add(new Genre(null, "Физика"));
    }
}
