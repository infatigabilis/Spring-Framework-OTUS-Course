package ru.otus.springframework.hw15.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static ru.otus.springframework.hw15.base.ValidationGroup.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class BookInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, groups = Update.class) @Null(groups = Insert.class)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Null(groups = Insert.class)
    @Builder.Default
    private Set<Author> authors = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Null(groups = Insert.class)
    @Builder.Default
    private Set<Genre> genres = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "bookInfo", cascade = CascadeType.REMOVE)
    @Null(groups = Insert.class)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
