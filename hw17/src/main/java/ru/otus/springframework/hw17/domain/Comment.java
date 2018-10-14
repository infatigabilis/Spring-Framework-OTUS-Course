package ru.otus.springframework.hw17.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Comment {
    @Size(min = 1)
    private String id;

    @NotBlank @Size(max = 255)
    private String text;

    @Builder.Default
    private Date createAt = new Date();

    @JsonIgnore
    private BookInfo bookInfo;
}
