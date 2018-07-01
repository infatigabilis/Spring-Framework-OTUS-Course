package ru.otus.springframework.hw01;

import ru.otus.springframework.hw01.config.AppConfig;
import ru.otus.springframework.hw01.dataset.Question;
import ru.otus.springframework.hw01.dataset.TestResult;
import ru.otus.springframework.hw01.service.TestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;

public class TestPrinterImpl implements TestPrinter {
    private static final String ANSWER_LETTERS = "abcdefg";

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final TestService service;

    public TestPrinterImpl(TestService service) {
        this.service = service;
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
        System.out.println("Добро пожаловать в программу тестирования калькуляторов!");
        System.out.println();
    }

    private void askName() throws IOException {
        System.out.print("Введите имя: ");
        reader.readLine();

        System.out.print("Введите фамилию: ");
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

            System.out.print("Ваш ответ: ");

            final int userAnswer = answerLetterToIndex(reader.readLine());
            service.answerCurrentQuestion(userAnswer);

            System.out.println();
        }
    }

    private void showResult() {
        System.out.println("Тест завершен");
        System.out.println();

        final TestResult userResult = service.getResult();

        if (AppConfig.SHOW_DETAILED_RESULT) {
            final String resultRes = userResult.getWrongs().isEmpty()
                    ? "все ответы верны! Поздравляем!!!"
                    : String.format("%s/%s", userResult.getSuccess(), userResult.getTotal());

            System.out.println("Ваш результат: " + resultRes);

            if (AppConfig.SHOW_WRONG_ANSWERS && !userResult.getWrongs().isEmpty()) {
                final String wrongAnswersRes = userResult.getWrongs().stream()
                        .map(Object::toString)
                        .reduce((i1, i2) -> i1 + ", " + i2)
                        .get();

                System.out.println("Неправильные ответы: " + wrongAnswersRes);
            }

        } else {
            System.out.println(userResult.getSuccess() == userResult.getTotal() ? "Тест пройден!" : "Тест не пройден...");
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
