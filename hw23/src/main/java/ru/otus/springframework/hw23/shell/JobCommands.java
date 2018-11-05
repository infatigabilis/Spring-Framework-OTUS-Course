package ru.otus.springframework.hw23.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.springframework.hw10.db.Seeder;
import ru.otus.springframework.hw10.repository.AuthorRepository;
import ru.otus.springframework.hw10.repository.BookInfoRepository;
import ru.otus.springframework.hw10.repository.CommentRepository;
import ru.otus.springframework.hw10.repository.GenreRepository;
import ru.otus.springframework.hw12.domain.Author;
import ru.otus.springframework.hw12.domain.BookInfo;
import ru.otus.springframework.hw12.domain.Comment;
import ru.otus.springframework.hw12.domain.Genre;

@ShellComponent
public class JobCommands {
    private static final String OK_RESPONSE = "Done";

    private final JobLauncher jobLauncher;
    private final Job migrationJob;

    private final BookInfoRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final MongoTemplate mongoTemplate;

    public JobCommands(JobLauncher jobLauncher, Job migrationJob, BookInfoRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository, MongoTemplate mongoTemplate) {
        this.jobLauncher = jobLauncher;
        this.migrationJob = migrationJob;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @ShellMethod("Seed test data to RDB")
    public String seedRdb() {
        new Seeder(bookRepository, authorRepository, genreRepository, commentRepository);
        return OK_RESPONSE;
    }

    @ShellMethod("Delete all data from RDB")
    public String purgeRdb() {
        commentRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();

        return OK_RESPONSE;
    }

    @ShellMethod("Delete all data from MongoDB")
    public String purgeMongo() {
        mongoTemplate.remove(new Query(), Comment.class);
        mongoTemplate.remove(new Query(), BookInfo.class);
        mongoTemplate.remove(new Query(), Author.class);
        mongoTemplate.remove(new Query(), Genre.class);

        return OK_RESPONSE;
    }

    @ShellMethod("Migrate data from RDB to MongoDB")
    public String migrate() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(migrationJob, new JobParameters());
        return OK_RESPONSE;
    }
}
