package ru.otus.springframework.hw12.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Document
public class Comment {
    @Id
    @Size(min = 1)
    private String id;

    @NotBlank @Size(max = 255)
    private String text;

    @Builder.Default
    private Date createAt = new Date();

    @DBRef
    private BookInfo bookInfo;
}
