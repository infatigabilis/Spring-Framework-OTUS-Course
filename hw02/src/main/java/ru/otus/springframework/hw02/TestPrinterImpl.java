package ru.otus.springframework.hw02;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw02.domain.Question;
import ru.otus.springframework.hw02.domain.TestResult;
import ru.otus.springframework.hw02.service.TestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

@Service
public class TestPrinterImpl implements TestPrinter {
    @Value("${app.show_detailed_result}") private boolean showDetailedResult;
    @Value("${app.show_wrong_answers}") private boolean showWrongAnswers;
    @Value("${app.locale}") private Locale locale;

    private static final String ANSWER_LETTERS = "abcdefg";

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final TestService service;
    private final MessageSource ms;

    public TestPrinterImpl(TestService service, MessageSource messageSource) {
        this.service = service;
        this.ms = messageSource;
    }

    public void run() {
        try {
            sayHello();
            askName();
            printDivider();
            testUser();
            printDivider();
            showResult();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sayHello() {
        System.out.println(ms.getMessage("hello", new String[0], locale));
        System.out.println();
    }

    private void askName() throws IOException {
        System.out.print(ms.getMessage("enter_your_name", new String[0], locale));
        reader.readLine();

        System.out.print(ms.getMessage("enter_your_last_name", new String[0], locale));
        reader.readLine();

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

            System.out.print(ms.getMessage("your_answer", new String[0], locale));

            final int userAnswer = answerLetterToIndex(reader.readLine());
            service.answerCurrentQuestion(userAnswer);

            System.out.println();
        }
    }

    private void showResult() {
        System.out.println(ms.getMessage("test_completed", new String[0], locale));
        System.out.println();

        final TestResult userResult = service.getResult();

        if (showDetailedResult) {
            final String resultRes = userResult.getWrongs().isEmpty()
                    ? ms.getMessage("all_answers_are_correct", new String[0], locale)
                    : String.format("%s/%s", userResult.getSuccess(), userResult.getTotal());

            System.out.println(ms.getMessage("your_result", new String[0], locale) + resultRes);

            if (showWrongAnswers && !userResult.getWrongs().isEmpty()) {
                final String wrongAnswersRes = userResult.getWrongs().stream()
                        .map(Object::toString)
                        .reduce((i1, i2) -> i1 + ", " + i2)
                        .get();

                System.out.println(ms.getMessage("wrong_answers", new String[0], locale) + wrongAnswersRes);
            }

        } else {
            System.out.println(userResult.getSuccess() == userResult.getTotal()
                    ? ms.getMessage("test_passed", new String[0], locale)
                    : ms.getMessage("test_failed", new String[0], locale));
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
