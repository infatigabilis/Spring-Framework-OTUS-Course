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
import ru.otus.springframework.hw10.repository.CommentRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class CommentStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final CommentRepository jpaCommentRepository;
    private final MongoTemplate mongoTemplate;


    public CommentStepConfig(StepBuilderFactory stepBuilderFactory, CommentRepository jpaCommentRepository, MongoTemplate mongoTemplate) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jpaCommentRepository = jpaCommentRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Bean
    public ItemReader<ru.otus.springframework.hw10.domain.Comment> commentReader() {
        AtomicInteger increment = new AtomicInteger(0);
        return () -> jpaCommentRepository.findAll(PageRequest.of(increment.getAndIncrement(), 1)).stream().findFirst().orElse(null);
    }

    @Bean
    public ItemProcessor<ru.otus.springframework.hw10.domain.Comment, ru.otus.springframework.hw12.domain.Comment> commentProcessor() {
        return jpaComment -> ru.otus.springframework.hw12.domain.Comment.builder()
                .id(jpaComment.getId() + "")
                .text(jpaComment.getText())
                .createAt(jpaComment.getCreateAt())
                .bookInfo(mongoTemplate.findById(jpaComment.getBookInfo().getId() + "", ru.otus.springframework.hw12.domain.BookInfo.class))
                .build();
    }

    @Bean
    public ItemWriter<ru.otus.springframework.hw12.domain.Comment> commentWriter() {
        return comments -> comments.forEach(mongoTemplate::save);
    }

    @Bean
    public Step commentStep(
            ItemReader<ru.otus.springframework.hw10.domain.Comment> commentReader,
            ItemProcessor<ru.otus.springframework.hw10.domain.Comment, ru.otus.springframework.hw12.domain.Comment> commentProcessor,
            ItemWriter<ru.otus.springframework.hw12.domain.Comment> commentWriter
    ) {
        return stepBuilderFactory.get("commentStep")
                .<ru.otus.springframework.hw10.domain.Comment, ru.otus.springframework.hw12.domain.Comment>chunk(5)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .build();
    }
}
