package ru.otus.springframework.hw23.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.springframework.hw10.repository.AuthorRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class AuthorStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorRepository jpaAuthorRepository;
    private final MongoTemplate mongoTemplate;


    public AuthorStepConfig(StepBuilderFactory stepBuilderFactory, AuthorRepository jpaAuthorRepository, MongoTemplate mongoTemplate) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jpaAuthorRepository = jpaAuthorRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Bean
    public ItemReader<ru.otus.springframework.hw10.domain.Author> authorReader() {
        AtomicInteger increment = new AtomicInteger(0);
        return () -> jpaAuthorRepository.findAll(PageRequest.of(increment.getAndIncrement(), 1)).stream().findFirst().orElse(null);
    }

    @Bean
    public ItemProcessor<ru.otus.springframework.hw10.domain.Author, ru.otus.springframework.hw12.domain.Author> authorProcessor() {
        return jpaAuthor -> ru.otus.springframework.hw12.domain.Author.builder()
                .id(jpaAuthor.getId() + "")
                .name(jpaAuthor.getName())
                .surname(jpaAuthor.getSurname())
                .build();
    }

    @Bean
    public ItemWriter<ru.otus.springframework.hw12.domain.Author> authorWriter() {
        return authors -> authors.forEach(mongoTemplate::save);
    }

    @Bean
    public Step authorStep(
            ItemReader<ru.otus.springframework.hw10.domain.Author> authorReader,
            ItemProcessor<ru.otus.springframework.hw10.domain.Author, ru.otus.springframework.hw12.domain.Author> authorProcessor,
            ItemWriter<ru.otus.springframework.hw12.domain.Author> authorWriter
    ) {
        return stepBuilderFactory.get("authorStep")
                .<ru.otus.springframework.hw10.domain.Author, ru.otus.springframework.hw12.domain.Author>chunk(5)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }
}
