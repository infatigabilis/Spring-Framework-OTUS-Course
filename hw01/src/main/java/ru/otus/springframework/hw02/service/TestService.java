package ru.otus.springframework.hw02.service;

import ru.otus.springframework.hw02.config.AppConfig;
import ru.otus.springframework.hw02.domain.Question;
import ru.otus.springframework.hw02.domain.TestResult;
import ru.otus.springframework.hw02.service.mapper.QuestionMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TestService {
    private final QuestionMapper questionMapper;

    private final List<Question> questions;

    private final TestResult result;
    private final Iterator<Question> questionIterator;
    private Question currentQuestion;

    public TestService(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;

        try {
            questions = questionMapper.parse(new File(getClass().getClassLoader().getResource(AppConfig.DATA_FILE_PATH).getFile()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        questionIterator = questions.iterator();
        result = new TestResult(questions.size());
    }

    public Question askNextQuestion() {
        currentQuestion = questionIterator.hasNext() ? questionIterator.next() : null;
        return currentQuestion;
    }

    public void answerCurrentQuestion(int answerIndex) {
        if (currentQuestion.getRightAnswer() == answerIndex) {
            result.setSuccess(result.getSuccess() + 1);
        } else {
            result.getWrongs().add(currentQuestion.getIndex());
        }
    }

    public TestResult getResult() {
        return result;
    }
}
