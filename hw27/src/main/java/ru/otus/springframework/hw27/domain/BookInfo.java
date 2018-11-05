package ru.otus.springframework.hw27.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
    @Min(value = 1)
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

    @JsonIgnore
    @OneToMany(mappedBy = "bookInfo", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
