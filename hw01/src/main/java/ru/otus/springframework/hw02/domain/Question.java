package ru.otus.springframework.hw02.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class Question {
    private final int index;
    private final String text;
    private final int rightAnswer;
    private final List<String> answers;
}
