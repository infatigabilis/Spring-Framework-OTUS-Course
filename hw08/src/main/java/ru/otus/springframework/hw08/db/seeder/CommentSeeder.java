package ru.otus.springframework.hw08.db.seeder;

import org.springframework.stereotype.Component;
import ru.otus.springframework.hw08.domain.Comment;
import ru.otus.springframework.hw08.repository.CommentRepository;

@Component
public class CommentSeeder {
    private final CommentRepository repository;

    public CommentSeeder(CommentRepository repository) {
        this.repository = repository;

        seed();
    }

    private void seed() {
        repository.add(Comment.builder().text("Aliquam eros leo, bibendum ac sem et").build(), 1);
        repository.add(Comment.builder().text("tempor cursus ante").build(), 1);
        repository.add(Comment.builder().text("Mauris erat quam, dapibus in massa vel, commodo pretium odio.").build(), 1);
        repository.add(Comment.builder().text("Praesent efficitur et arcu at scelerisque.").build(), 2);
        repository.add(Comment.builder().text("Nulla ac efficitur sapien").build(), 3);
        repository.add(Comment.builder().text("Mauris sit amet molestie est, et lacinia sem. In hac habitasse platea dictumst. Morbi ex metus").build(), 5);
        repository.add(Comment.builder().text("blandit vitae metus a, condimentum consectetur erat").build(), 5);
    }
}
