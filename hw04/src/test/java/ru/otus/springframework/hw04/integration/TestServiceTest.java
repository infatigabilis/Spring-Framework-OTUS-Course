package ru.otus.springframework.hw04.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springframework.hw04.Runner;
import ru.otus.springframework.hw04.domain.Question;
import ru.otus.springframework.hw04.domain.TestResult;
import ru.otus.springframework.hw04.service.TestService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest @RunWith(SpringRunner.class)
public class TestServiceTest {
    @MockBean private Runner runner;
    @Autowired private TestService service;

    @Test
    public void success() {
        Question actual1 = service.askNextQuestion();
        assertEquals(1, actual1.getIndex());
        assertEquals("question1", actual1.getText());

        service.answerCurrentQuestion(10);

        Question actual2 = service.askNextQuestion();
        assertEquals(2, actual2.getIndex());
        assertEquals("question2", actual2.getText());

        service.answerCurrentQuestion(1);

        assertNull(service.askNextQuestion());

        final TestResult result = service.getResult();
        assertEquals(2, result.getTotal());
        assertEquals(1, result.getSuccess());
        assertEquals(1, result.getWrongs().size());
        assertEquals(2, (int) result.getWrongs().get(0));
    }
}
