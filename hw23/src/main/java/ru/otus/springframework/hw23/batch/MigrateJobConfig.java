package ru.otus.springframework.hw23.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrateJobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    public MigrateJobConfig(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job migrationJob(Step authorStep, Step genreStep, Step bookStep, Step commentStep) {
        return jobBuilderFactory.get("migrate_to_mongo_job")
                .incrementer(new RunIdIncrementer())
                .flow(authorStep)
                .next(genreStep)
                .next(bookStep)
                .next(commentStep)
                .end()
                .build();
    }
}
