package ru.netology.javacore;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class TodoServer {
    Logger logger = new Logger();
    protected Todos todos;
    protected final int port;

    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;
    }

    public void start() throws IOException {
        out.println("Starting server at " + port + "...");
        ServerSocket server = new ServerSocket(port);
        out.println("Сервер запущен.");
        while (true) {
            try (Socket client = server.accept();
                 PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                writer.println("Есть подключение");

                String request = reader.readLine();

                writer.println(requestTodos(request));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String requestTodos(String request) {
        JSONParser parser = new JSONParser();
        String type;
        String task;
        try {
            Object obj = parser.parse(request);
            JSONObject ParsedJson = (JSONObject) obj;

            type = (String) ParsedJson.get("type");
            task = (String) ParsedJson.get("task");

        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
        switch (type) {
            case "add":
                todos.addTask(task);
                logger.logEntry(new Client(type,task));
                break;
            case "remove":
                todos.removeTask(task);
                logger.logEntry(new Client(type,task));
                break;
            case "restore":
                if (logger.todosList.isEmpty()) {
                    break;
                }
                Client client = logger.getEntry();
                if (client.getType().equals("add")) {
                    todos.removeTask(client.getTask());
                } else {
                    todos.addTask(client.getTask());
                }
                logger.removeLogEntry();
                break;
            default:
                return "не корректный ввод";

        }
        return todos.getAllTasks();
    }
}
