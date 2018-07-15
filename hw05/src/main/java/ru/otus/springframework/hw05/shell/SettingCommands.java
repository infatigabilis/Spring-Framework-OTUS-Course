package ru.otus.springframework.hw05.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class SettingCommands {

    @ShellMethod(key = "name", value = "Identify your name")
    public String setName(@ShellOption String name) {
        return String.format("Your name have been changed to '%s'", name);
    }

    @ShellMethod(key = "surname", value = "Identify your surname")
    public String setSurname(@ShellOption String surname) {
        return String.format("Your name have been changed to '%s'", surname);
    }
}
