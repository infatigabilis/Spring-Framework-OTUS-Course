package ru.otus.springframework.hw06.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
public class BookInfo {
    private @Min(0) long id;

    @NotBlank @Size(max = 255)
    private String title;

    private String description;

    private List<Author> authors = new ArrayList<>();

    private List<Genre> genres = new ArrayList<>();
}
