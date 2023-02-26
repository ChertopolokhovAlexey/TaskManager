package ru.netology.javacore;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    protected List<Client> todosList = new ArrayList<>();

    public Logger() {
    }

    public void logEntry(Client client) {
        todosList.add(client);
    }

    public Client getEntry() {
        return todosList.get(todosList.size() - 1);
    }

    public void removeLogEntry() {
        todosList.remove(todosList.get(todosList.size() - 1));
    }
}
