package ru.otus.springframework.hw02.service.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.otus.springframework.hw02.domain.Question;
import ru.otus.springframework.hw02.domain.TestResult;
import ru.otus.springframework.hw02.service.TestService;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceTest {
    @Mock private QuestionMapper questionMapper;
    private TestService service;

    @Test
    public void success() throws IOException {
        final Question q1 = new Question(1, "1", 1, new ArrayList<String>() {{ add("1"); }});
        final Question q2 = new Question(2, "2", 2, new ArrayList<String>() {{ add("2"); }});

        when(questionMapper.parse(any())).thenReturn(new ArrayList<Question>() {{
            add(q1);
            add(q2);
        }});

        service = new TestService(questionMapper);
        service.init();

        Question actual1 = service.askNextQuestion();
        assertEquals(1, actual1.getIndex());
        assertEquals("1", actual1.getText());

        service.answerCurrentQuestion(1);

        Question actual2 = service.askNextQuestion();
        assertEquals(2, actual2.getIndex());
        assertEquals("2", actual2.getText());

        service.answerCurrentQuestion(1);

        assertNull(service.askNextQuestion());

        final TestResult result = service.getResult();
        assertEquals(2, result.getTotal());
        assertEquals(1, result.getSuccess());
        assertEquals(1, result.getWrongs().size());
        assertEquals(2, (int) result.getWrongs().get(0));
    }
}
