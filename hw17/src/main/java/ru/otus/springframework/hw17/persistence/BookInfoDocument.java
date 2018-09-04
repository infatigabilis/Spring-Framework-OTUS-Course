package ru.otus.springframework.hw17.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Document
public class BookInfoDocument {
    @Id
    @Size(min = 1)
    private String id;

    @NotBlank
    @Size(max = 255)
    private String title;

    private String description;

    @Builder.Default
    private List<String> authorIds = new ArrayList<>();

    @Builder.Default
    private List<String> genreIds = new ArrayList<>();
}
