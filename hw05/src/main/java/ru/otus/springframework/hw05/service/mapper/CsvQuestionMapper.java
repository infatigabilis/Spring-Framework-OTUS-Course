package ru.otus.springframework.hw05.service.mapper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw05.domain.Question;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CsvQuestionMapper implements QuestionMapper {
    public List<Question> parse(File file) throws IOException {
        AtomicInteger i = new AtomicInteger();

        return CSVParser.parse(file, Charset.forName("UTF-8"), CSVFormat.DEFAULT).getRecords()
                .stream()
                .map(CSVRecord::iterator)
                .map(iter ->
                        new Question(
                                i.incrementAndGet(),
                                iter.next(),
                                Integer.parseInt(iter.next()),
                                new ArrayList<String>() {{ while(iter.hasNext()) add(iter.next()); }}
                        )
                )
                .collect(Collectors.toList());
    }
}
