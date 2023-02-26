package ru.netology.javacore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    protected final static int PORT = 8989;
    protected final static String HOST = "127.0.0.1";
    protected String type;
    protected String task;


    public Client(String type, String task) {
        this.type = type;
        this.task = task;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println(reader.readLine());
            System.out.println("Введите тип операции (ADD, REMOVE, RESTORE).");//message for user
            String type = (scanner.nextLine()).toLowerCase();
            String task = null;
            if (!type.equals("restore")) {
                System.out.println("ВВедите задачу");
                task = (scanner.nextLine()).toLowerCase()+"\n";
            }

            String jsonString = toJson(type, task);
            writer.println(jsonString);

            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static String toJson(String type, String task) {
        Client gsonClient = new Client(type, task);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(gsonClient);
    }

    public String getTask() {
        return task;
    }

    public String getType() {
        return type;
    }
}
