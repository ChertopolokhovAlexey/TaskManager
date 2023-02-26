package ru.netology.javacore;

import java.util.*;
import java.util.stream.Collectors;

public class Todos {
    protected final static int SIZE = 7;
    protected Set<String> todosList = new TreeSet<>();

    public Todos() {
    }

    public void addTask(String task) {
        if (todosList.size() < SIZE) {
            this.todosList.add(task);
        }
    }

    public void removeTask(String task) {
        if (!todosList.isEmpty()) {
            for (String todo : todosList) {
                if (todo.equals(task)) {
                    this.todosList.remove(todo);
                    break;
                }
            }
        }
    }

    public String getAllTasks() {
        return todosList
                .stream()
                .collect(Collectors.joining());
    }
}
