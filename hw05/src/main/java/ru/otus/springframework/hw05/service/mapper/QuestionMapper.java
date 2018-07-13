package ru.otus.springframework.hw05.service.mapper;


import ru.otus.springframework.hw05.domain.Question;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface QuestionMapper {
    List<Question> parse(File file) throws IOException;
}
