package ru.otus.springframework.hw12.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Document
public class BookInfo {
    @Id
    @Size(min = 1)
    private String id;

    @NotBlank @Size(max = 255)
    private String title;

    private String description;

    @DBRef
    @Builder.Default
    private Set<Author> authors = new LinkedHashSet<>();

    @DBRef
    @Builder.Default
    private Set<Genre> genres = new LinkedHashSet<>();

    @DBRef
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
