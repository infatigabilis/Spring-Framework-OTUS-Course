package ru.otus.springframework.hw21.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.springframework.hw21.base.ValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, groups = ValidationGroup.Update.class) @Null(groups = ValidationGroup.Insert.class)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;
}
