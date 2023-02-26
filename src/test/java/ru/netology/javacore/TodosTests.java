package ru.netology.javacore;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Set;
import java.util.TreeSet;


public class TodosTests {
    Set<String> expectedSet = new TreeSet<>();
    Todos todos = new Todos();
    TodoServer todoServer = new TodoServer(8989, todos);


    public String expectedGetAllTasks(Set<String> expectedSet) {
        return String.join("", expectedSet);
    }


    @Test
    public void addTask() {
        expectedSet.add("ddd");
        expectedSet.add("ggg");
        expectedSet.add("bbb");
        expectedSet.add("aaa");
        expectedSet.add("qqq");

        String expectation = expectedGetAllTasks(expectedSet);


        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"ddd\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"ggg\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"bbb\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"aaa\"}");

        String result = todoServer.requestTodos("{\"type\":\"add\",\"task\":\"qqq\"}");

        Assertions.assertEquals(expectation, result);

    }

    @Test
    public void removeTask() {
        expectedSet.add("ddd");
        expectedSet.add("ggg");
        expectedSet.add("bbb");
        expectedSet.add("aaa");
        expectedSet.removeIf(todos->todos.equals("bbb"));
        String expectation = expectedGetAllTasks(expectedSet);

        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"ddd\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"ggg\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"bbb\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"aaa\"}");

        String result = todoServer.requestTodos("{\"type\":\"remove\",\"task\":\"bbb\"}");

        Assertions.assertEquals(expectation, result);


    }

    @Test
    public void restoreTask() {
        expectedSet.add("ddd");
        expectedSet.add("ggg");
        expectedSet.add("bbb");
        expectedSet.add("aaa");

        String expectation = expectedGetAllTasks(expectedSet);

        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"ddd\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"ggg\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"bbb\"}");
        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"aaa\"}");


        todoServer.requestTodos("{\"type\":\"add\",\"task\":\"qqq\"}");
        todoServer.requestTodos("{\"type\":\"remove\",\"task\":\"aaa\"}");
        todoServer.requestTodos("{\"type\":\"restore\"}");
        String result = todoServer.requestTodos("{\"type\":\"restore\"}");

        Assertions.assertEquals(expectation, result);
    }
}
