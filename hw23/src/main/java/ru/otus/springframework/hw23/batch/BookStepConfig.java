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
import ru.otus.springframework.hw10.repository.BookInfoRepository;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
public class BookStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final BookInfoRepository jpaBookInfoRepository;
    private final MongoTemplate mongoTemplate;


    public BookStepConfig(StepBuilderFactory stepBuilderFactory, BookInfoRepository jpaBookInfoRepository, MongoTemplate mongoTemplate) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jpaBookInfoRepository = jpaBookInfoRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Bean
    public ItemReader<ru.otus.springframework.hw10.domain.BookInfo> bookReader() {
        AtomicInteger increment = new AtomicInteger(0);
        return () -> jpaBookInfoRepository.findAll(PageRequest.of(increment.getAndIncrement(), 1)).stream().findFirst().orElse(null);
    }

    @Bean
    public ItemProcessor<ru.otus.springframework.hw10.domain.BookInfo, ru.otus.springframework.hw12.domain.BookInfo> bookProcessor() {
        return jpaBook -> ru.otus.springframework.hw12.domain.BookInfo.builder()
                .id(jpaBook.getId() + "")
                .title(jpaBook.getTitle())
                .description(jpaBook.getTitle())
                .authors(
                        jpaBook.getAuthors().stream()
                                .map(jpaAuthor -> mongoTemplate.findById(jpaAuthor.getId() + "", ru.otus.springframework.hw12.domain.Author.class))
                                .collect(Collectors.toSet())
                )
                .genres(
                        jpaBook.getGenres().stream()
                                .map(jpaGenre -> mongoTemplate.findById(jpaGenre.getId() + "", ru.otus.springframework.hw12.domain.Genre.class))
                                .collect(Collectors.toSet())
                )
                .comments(
                        jpaBook.getComments().stream()
                                .map(jpaComment -> ru.otus.springframework.hw12.domain.Comment.builder()
                                        .id(jpaComment.getId() + "")
                                        .text(jpaComment.getText())
                                        .createAt(jpaComment.getCreateAt())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Bean
    public ItemWriter<ru.otus.springframework.hw12.domain.BookInfo> bookWriter() {
        return books -> books.forEach(mongoTemplate::save);
    }

    @Bean
    public Step bookStep(
            ItemReader<ru.otus.springframework.hw10.domain.BookInfo> bookReader,
            ItemProcessor<ru.otus.springframework.hw10.domain.BookInfo, ru.otus.springframework.hw12.domain.BookInfo> bookProcessor,
            ItemWriter<ru.otus.springframework.hw12.domain.BookInfo> bookWriter
    ) {
        return stepBuilderFactory.get("bookStep")
                .<ru.otus.springframework.hw10.domain.BookInfo, ru.otus.springframework.hw12.domain.BookInfo>chunk(5)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }
}
