package ru.otus.springframework.hw02.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @RequiredArgsConstructor
public class TestResult {
    private final int total;
    private int success;
    private final List<Integer> wrongs = new ArrayList<>();
}
