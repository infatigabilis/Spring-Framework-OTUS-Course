package ru.otus.springframework.hw05.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springframework.hw05.config.AppSettings;
import ru.otus.springframework.hw05.domain.Question;
import ru.otus.springframework.hw05.domain.TestResult;
import ru.otus.springframework.hw05.service.TestService;
import ru.otus.springframework.hw05.service.mapper.CsvQuestionMapper;
import ru.otus.springframework.hw05.service.mapper.QuestionMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest @RunWith(SpringRunner.class)
public class TestServiceTest {
    @Autowired private TestService service;

    @Configuration
    static class TestConfiguration {
        @Bean
        public AppSettings appSettings() {
            return new AppSettings() {{
                setDataFilePath("csv_mapper_test_data.csv");
            }};
        }

        @Bean
        public QuestionMapper questionMapper() {
            return new CsvQuestionMapper();
        }

        @Bean
        public TestService testService() {
            return new TestService(appSettings(), questionMapper());
        }
    }

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
