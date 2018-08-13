package ru.otus.springframework.hw10.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;
}
