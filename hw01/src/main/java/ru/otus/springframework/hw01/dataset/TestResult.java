package ru.otus.springframework.hw01.dataset;

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