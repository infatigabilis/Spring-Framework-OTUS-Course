package ru.otus.springframework.hw08.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String text;

    @Column(nullable = false)
    @Builder.Default
    private Date createAt = new Date();

    @ManyToOne
    private BookInfo bookInfo;
}
