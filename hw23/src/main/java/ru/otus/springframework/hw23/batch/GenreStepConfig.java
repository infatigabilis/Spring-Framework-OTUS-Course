package ru.otus.springframework.hw23.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.springframework.hw10.repository.GenreRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class GenreStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final GenreRepository jpaGenreRepository;
    private final MongoTemplate mongoTemplate;


    public GenreStepConfig(StepBuilderFactory stepBuilderFactory, GenreRepository jpaGenreRepository, MongoTemplate mongoTemplate) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jpaGenreRepository = jpaGenreRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Bean
    public ItemReader<ru.otus.springframework.hw10.domain.Genre> genreReader() {
        AtomicInteger increment = new AtomicInteger(0);
        return () -> jpaGenreRepository.findAll(PageRequest.of(increment.getAndIncrement(), 1)).stream().findFirst().orElse(null);
    }

    @Bean
    public ItemProcessor<ru.otus.springframework.hw10.domain.Genre, ru.otus.springframework.hw12.domain.Genre> genreProcessor() {
        return jpaGenre -> ru.otus.springframework.hw12.domain.Genre.builder()
                .id(jpaGenre.getId() + "")
                .name(jpaGenre.getName())
                .build();
    }

    @Bean
    public ItemWriter<ru.otus.springframework.hw12.domain.Genre> genreWriter() {
        return genres -> genres.forEach(mongoTemplate::save);
    }

    @Bean
    public Step genreStep(
            ItemReader<ru.otus.springframework.hw10.domain.Genre> genreReader,
            ItemProcessor<ru.otus.springframework.hw10.domain.Genre, ru.otus.springframework.hw12.domain.Genre> genreProcessor,
            ItemWriter<ru.otus.springframework.hw12.domain.Genre> genreWriter
    ) {
        return stepBuilderFactory.get("genreStep")
                .<ru.otus.springframework.hw10.domain.Genre, ru.otus.springframework.hw12.domain.Genre>chunk(5)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }
}
