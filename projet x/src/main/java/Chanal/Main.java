package Chanal;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tv_programs";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "teremok007";

    public static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = connectToDatabase()) {
            System.out.println("Подключение к базе данных выполнено!");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nМеню:");
                System.out.println("1. Просмотреть все программы");
                System.out.println("2. Добавить новую программу");
                System.out.println("3. Изменить существующую программу");
                System.out.println("4. Удалить программу");
                System.out.println("5. Управление каналами");
                System.out.println("6. Управление жанрами");
                System.out.println("0. Выход");
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> viewPrograms(connection);
                    case 2 -> addProgram(connection, scanner);
                    case 3 -> editProgram(connection, scanner);
                    case 4 -> deleteProgram(connection, scanner);
                    case 5 -> manageChannels(connection, scanner);
                    case 6 -> manageGenres(connection, scanner);
                    case 0 -> {
                        System.out.println("Выход из программы...");
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Повторите попытку.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

    private static void viewPrograms(Connection connection) throws SQLException {
        String query = """
                SELECT p.id, p.program_name, c.name AS channel, g.name AS genre, p.day_of_week, p.start_time
                FROM programs p
                JOIN channels c ON p.channel_id = c.id
                JOIN genres g ON p.genre_id = g.id
                """;
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            System.out.printf("%-5s %-20s %-15s %-15s %-10s %-10s%n", "ID", "Программа", "Канал", "Жанр", "День", "Время");
            while (resultSet.next()) {
                System.out.printf("%-5d %-20s %-15s %-15s %-10s %-10s%n",
                        resultSet.getInt("id"),
                        resultSet.getString("program_name"),
                        resultSet.getString("channel"),
                        resultSet.getString("genre"),
                        resultSet.getString("day_of_week"),
                        resultSet.getString("start_time"));
            }
        }
    }

    private static void addProgram(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите название программы: ");
        String programName = scanner.nextLine();
        System.out.print("Введите ID канала: ");
        int channelId = scanner.nextInt();
        System.out.print("Введите ID жанра: ");
        int genreId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Введите день недели: ");
        String dayOfWeek = scanner.nextLine();
        System.out.print("Введите время начала (ЧЧ:ММ:СС): ");
        String startTime = scanner.nextLine();

        String query = "INSERT INTO programs (channel_id, genre_id, program_name, day_of_week, start_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, channelId);
            preparedStatement.setInt(2, genreId);
            preparedStatement.setString(3, programName);
            preparedStatement.setString(4, dayOfWeek);
            preparedStatement.setString(5, startTime);
            preparedStatement.executeUpdate();
            System.out.println("Программа успешно добавлена.");
        }
    }

    private static void editProgram(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите ID программы для изменения: ");
        int programId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Введите новое название программы: ");
        String programName = scanner.nextLine();
        System.out.print("Введите новый ID канала: ");
        int channelId = scanner.nextInt();
        System.out.print("Введите новый ID жанра: ");
        int genreId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Введите новый день недели: ");
        String dayOfWeek = scanner.nextLine();
        System.out.print("Введите новое время начала (ЧЧ:ММ:СС): ");
        String startTime = scanner.nextLine();

        String query = "UPDATE programs SET channel_id = ?, genre_id = ?, program_name = ?, day_of_week = ?, start_time = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, channelId);
            preparedStatement.setInt(2, genreId);
            preparedStatement.setString(3, programName);
            preparedStatement.setString(4, dayOfWeek);
            preparedStatement.setString(5, startTime);
            preparedStatement.setInt(6, programId);
            preparedStatement.executeUpdate();
            System.out.println("Программа успешно обновлена.");
        }
    }

    private static void deleteProgram(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите ID программы для удаления: ");
        int programId = scanner.nextInt();

        String query = "DELETE FROM programs WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, programId);
            preparedStatement.executeUpdate();
            System.out.println("Программа успешно удалена.");
        }
    }

    private static void manageChannels(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("1. Добавить канал");
        System.out.println("2. Удалить канал");
        System.out.print("Выберите действие: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1 -> {
                System.out.print("Введите название канала: ");
                String channelName = scanner.nextLine();
                String query = "INSERT INTO channels (name) VALUES (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, channelName);
                    preparedStatement.executeUpdate();
                    System.out.println("Канал успешно добавлен.");
                }
            }
            case 2 -> {
                System.out.print("Введите ID канала для удаления: ");
                int channelId = scanner.nextInt();
                String query = "DELETE FROM channels WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, channelId);
                    preparedStatement.executeUpdate();
                    System.out.println("Канал успешно удалён.");
                }
            }
            default -> System.out.println("Неверный выбор.");
        }
    }

    private static void manageGenres(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("1. Добавить жанр");
        System.out.println("2. Удалить жанр");
        System.out.print("Выберите действие: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1 -> {
                System.out.print("Введите название жанра: ");
                String genreName = scanner.nextLine();
                String query = "INSERT INTO genres (name) VALUES (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, genreName);
                    preparedStatement.executeUpdate();
                    System.out.println("Жанр успешно добавлен.");
                }
            }
            case 2 -> {
                System.out.print("Введите ID жанра для удаления: ");
                int genreId = scanner.nextInt();
                String query = "DELETE FROM genres WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, genreId);
                    preparedStatement.executeUpdate();
                    System.out.println("Жанр успешно удалён.");
                }
            }
            default -> System.out.println("Неверный выбор.");
        }
    }
}
