package ru.otus.springframework.hw02.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw02.domain.Question;
import ru.otus.springframework.hw02.domain.TestResult;
import ru.otus.springframework.hw02.service.mapper.QuestionMapper;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class TestService {
    private final String dataFilePath;

    private final QuestionMapper questionMapper;

    private List<Question> questions;

    private TestResult result;
    private Iterator<Question> questionIterator;
    private Question currentQuestion;

    public TestService(QuestionMapper questionMapper, @Value("${app.data_file}") String dataFilePath) {
        this.questionMapper = questionMapper;
        this.dataFilePath = dataFilePath;

        init();
    }

    @PostConstruct
    public void init() {
        try {
            questions = questionMapper.parse(new File(getClass().getClassLoader().getResource(dataFilePath).getFile()));
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
