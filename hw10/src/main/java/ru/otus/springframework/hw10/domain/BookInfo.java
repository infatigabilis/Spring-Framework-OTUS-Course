package ru.otus.springframework.hw10.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class BookInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Author> authors = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Genre> genres = new LinkedHashSet<>();

    @OneToMany(mappedBy = "bookInfo")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
