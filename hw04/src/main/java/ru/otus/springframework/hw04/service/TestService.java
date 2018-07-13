package ru.otus.springframework.hw04.service;

import org.springframework.stereotype.Service;
import ru.otus.springframework.hw04.config.AppSettings;
import ru.otus.springframework.hw04.domain.Question;
import ru.otus.springframework.hw04.domain.TestResult;
import ru.otus.springframework.hw04.service.mapper.QuestionMapper;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class TestService {
    private final AppSettings appSettings;
    private final QuestionMapper questionMapper;

    private TestResult result;
    private Iterator<Question> questionIterator;
    private Question currentQuestion;

    public TestService(AppSettings appSettings, QuestionMapper questionMapper) {
        this.appSettings = appSettings;
        this.questionMapper = questionMapper;

        init();
    }

    @PostConstruct
    public void init() {
        try {
            final String filePath = getClass().getClassLoader().getResource(appSettings.getDataFilePath()).getFile();
            final List<Question> questions = questionMapper.parse(new File(filePath));

            questionIterator = questions.iterator();
            result = new TestResult(questions.size());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
