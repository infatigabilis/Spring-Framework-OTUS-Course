package ru.otus.springframework.hw01.service.mapper;

import ru.otus.springframework.hw01.domain.Question;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface QuestionMapper {
    List<Question> parse(File file) throws IOException;
}
