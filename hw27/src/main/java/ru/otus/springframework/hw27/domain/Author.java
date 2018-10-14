package ru.otus.springframework.hw27.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;
}
