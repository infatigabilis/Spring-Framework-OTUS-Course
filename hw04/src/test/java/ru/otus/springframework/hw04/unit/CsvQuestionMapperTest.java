package ru.otus.springframework.hw04.unit;

import org.junit.Test;
import ru.otus.springframework.hw04.domain.Question;
import ru.otus.springframework.hw04.service.mapper.CsvQuestionMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvQuestionMapperTest {
    private final CsvQuestionMapper csvQuestionMapper = new CsvQuestionMapper();

    @Test
    public void success() throws IOException {
        final File file = new File(getClass().getClassLoader().getResource("csv_mapper_test_data.csv").getFile());
        List<Question> actual = csvQuestionMapper.parse(file);

        assertEquals(2, actual.size());
        assertEquals(1, actual.get(0).getIndex());
        assertEquals(2, actual.get(1).getIndex());
        assertEquals("question2", actual.get(1).getText());
        assertEquals(10, actual.get(0).getRightAnswer());
        assertEquals(5, actual.get(0).getAnswers().size());
        assertEquals("answer5", actual.get(1).getAnswers().get(0));
    }
}
