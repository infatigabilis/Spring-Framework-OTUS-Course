package ru.otus.springframework.hw19.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Optional;

import static ru.otus.springframework.hw19.base.ValidationGroup.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
@JsonDeserialize(converter=Comment.CommentPostConstruct.class)
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Insert.class)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String text;

    @Column(nullable = false)
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createAt = new Date();

    @JsonIgnore
    @ManyToOne
    private BookInfo bookInfo;


    public static class CommentPostConstruct extends StdConverter<Comment, Comment> {

        @Override
        public Comment convert(Comment comment) {
            comment.setCreateAt(Optional.ofNullable(comment.getCreateAt()).orElse(new Date()));
            return comment;
        }
    }
}
