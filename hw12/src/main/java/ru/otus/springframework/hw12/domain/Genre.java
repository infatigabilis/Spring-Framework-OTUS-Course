package ru.otus.springframework.hw12.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Document
public class Genre {
    @Id
    @Size(min = 1)
    private String id;

    @NotBlank @Size(max = 255)
    private String name;
}
