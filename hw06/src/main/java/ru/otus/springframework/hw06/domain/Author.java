package ru.otus.springframework.hw06.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
public class Author {
    private @Min(0) long id;

    @NotBlank @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;
}
