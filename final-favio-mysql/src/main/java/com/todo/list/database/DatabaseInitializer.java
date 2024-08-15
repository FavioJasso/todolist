package com.todo.list.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.todo.list.database.DatabaseConfigs.*;

public class DatabaseInitializer {

    public static void executeSqlScript() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            InputStream is = DatabaseInitializer.class.getResourceAsStream("/schema.sql");
            String sqlScript = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Splitting the SQL script into individual SQL statements
            String[] sqlStatements = sqlScript.split(";");

            // Executing each SQL statement separately
            for (String sql : Arrays.asList(sqlStatements)) {
                if (sql.trim().length() > 0) {
                    stmt.execute(sql.trim() + ";"); // Ensure each statement ends with a semicolon
                }
            }

            System.out.println("Database and table created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS ToDoListDB");
            System.out.println("Database created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable() {
        try (Connection conn = DriverManager.getConnection(URL + "ToDoListDB", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ToDoItems (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "description TEXT, " +
                    "dueDate DATETIME, " +
                    "status ENUM('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') NOT NULL DEFAULT 'NOT_STARTED')");
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}