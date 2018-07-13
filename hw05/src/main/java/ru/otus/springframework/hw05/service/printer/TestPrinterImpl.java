package ru.otus.springframework.hw05.service.printer;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw05.config.AppSettings;
import ru.otus.springframework.hw05.domain.Question;
import ru.otus.springframework.hw05.domain.TestResult;
import ru.otus.springframework.hw05.service.TestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TestPrinterImpl implements TestPrinter {
    private static final String ANSWER_LETTERS = "abcdefg";

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final AppSettings appSettings;
    private final TestService service;
    private final MessageSource ms;

    public TestPrinterImpl(AppSettings appSettings, TestService service, MessageSource messageSource) {
        this.appSettings = appSettings;
        this.service = service;
        this.ms = messageSource;
    }

    public void run() {
        try {
            sayHello();
            testUser();
            printDivider();
            showResult();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sayHello() {
        System.out.println(ms.getMessage("hello", new String[0], appSettings.getLocale()));
        System.out.println();
    }

    private void testUser() throws IOException {
        Question question;
        while((question = service.askNextQuestion()) != null) {

            System.out.println(String.format("%s. %s", question.getIndex(), question.getText()));
            System.out.println();

            for (int i = 0; i < question.getAnswers().size(); i++)
                System.out.println(String.format("\t%s) %s", answerIndexToLetter(i), question.getAnswers().get(i)));

            System.out.println();

            System.out.print(ms.getMessage("your_answer", new String[0], appSettings.getLocale()));

            final int userAnswer = answerLetterToIndex(reader.readLine());
            service.answerCurrentQuestion(userAnswer);

            System.out.println();
        }
    }

    private void showResult() {
        System.out.println(ms.getMessage("test_completed", new String[0], appSettings.getLocale()));
        System.out.println();

        final TestResult userResult = service.getResult();

        if (appSettings.isShowDetailedResult()) {
            final String resultRes = userResult.getWrongs().isEmpty()
                    ? ms.getMessage("all_answers_are_correct", new String[0], appSettings.getLocale())
                    : String.format("%s/%s", userResult.getSuccess(), userResult.getTotal());

            System.out.println(ms.getMessage("your_result", new String[0], appSettings.getLocale()) + resultRes);

            if (appSettings.isShowWrongAnswers() && !userResult.getWrongs().isEmpty()) {
                final String wrongAnswersRes = userResult.getWrongs().stream()
                        .map(Object::toString)
                        .reduce((i1, i2) -> i1 + ", " + i2)
                        .get();

                System.out.println(ms.getMessage("wrong_answers", new String[0], appSettings.getLocale()) + wrongAnswersRes);
            }

        } else {
            System.out.println(userResult.getSuccess() == userResult.getTotal()
                    ? ms.getMessage("test_passed", new String[0], appSettings.getLocale())
                    : ms.getMessage("test_failed", new String[0], appSettings.getLocale()));
        }
    }


    private void printDivider() {
        System.out.println("------------------------------------------");
        System.out.println();
    }

    private String answerIndexToLetter(int index) {
        return "" + ANSWER_LETTERS.charAt(index);
    }

    private int answerLetterToIndex(String letter) {
        return ANSWER_LETTERS.indexOf(letter) + 1;
    }
}
