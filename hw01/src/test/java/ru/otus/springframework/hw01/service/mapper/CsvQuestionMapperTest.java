package ru.otus.springframework.hw01.service.mapper;

import org.junit.Test;
import ru.otus.springframework.hw01.dataset.Question;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvQuestionMapperTest {
    private final CsvQuestionMapper csvQuestionMapper = new CsvQuestionMapper();

    @Test
    public void success() throws IOException {
        final String csv = "question1,10,answer1,answer2,answer3,answer4,answer5\n" +
                           "question2,2,answer5,answer4";

        final String filename = "csv-test-" + UUID.randomUUID();
        File file = File.createTempFile(filename, null);
        Files.write(file.toPath(), csv.getBytes());

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
