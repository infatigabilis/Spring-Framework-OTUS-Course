package ru.otus.springframework.hw17.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class BookInfo {
    @Size(min = 1)
    private String id;

    @NotBlank @Size(max = 255)
    private String title;

    private String description;

    @Builder.Default
    private List<Author> authors = new ArrayList<>();

    @Builder.Default
    private List<Genre> genres = new ArrayList<>();
}
