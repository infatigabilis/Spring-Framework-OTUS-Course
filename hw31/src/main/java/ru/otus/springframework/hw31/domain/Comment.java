package ru.otus.springframework.hw31.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.springframework.hw31.domain.base.ValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = ValidationGroup.Insert.class)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String text;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createAt = new Date();

    @JsonIgnore
    @ManyToOne
    private BookInfo bookInfo;
}
