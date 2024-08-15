package com.todo.list.database;


import com.todo.list.implementation.BasicToDoItem;
import com.todo.list.implementation.Status;
import com.todo.list.service.DatabaseService;
import com.todo.list.service.ToDoItem;

import static com.todo.list.database.DatabaseConfigs.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ToDoListMySQLAdapter implements DatabaseService {


    public ToDoListMySQLAdapter() {
        Connection testConnection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            testConnection = connect();
            System.out.println("Connection to MySQL database established successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC driver");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish a connection to the MySQL database");
        } finally {
            if (testConnection != null) {
                try {
                    testConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
    }

    @Override
    public List<ToDoItem> getToDoList() {
        List<ToDoItem> toDoItems = new ArrayList<>();
        String query = "SELECT id, description, dueDate, status FROM " + TABLE_NAME;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ToDoItem item = new BasicToDoItem();
                item.setId(rs.getInt("id"));
                item.setItemDetails(rs.getString("description"));
                item.setDueDate(rs.getDate("dueDate"));
                item.setStatus(Status.valueOf(rs.getString("status")));
                toDoItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDoItems;
    }

    @Override
    public ToDoItem addTask(ToDoItem newItem) {
        String query = "INSERT INTO " + TABLE_NAME + " (description, dueDate, status) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newItem.getItemDetails());
            pstmt.setDate(2, new java.sql.Date(newItem.getDueDate().getTime()));
            pstmt.setString(3, newItem.getStatus().name());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    newItem.setId(rs.getInt(1));
                    return newItem;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateTask(ToDoItem updatedItem) {
        String query = "UPDATE " + TABLE_NAME + " SET description = ?, dueDate = ?, status = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, updatedItem.getItemDetails());
            pstmt.setDate(2, new java.sql.Date(updatedItem.getDueDate().getTime()));
            pstmt.setString(3, updatedItem.getStatus().name());
            pstmt.setInt(4, updatedItem.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeTask(ToDoItem item) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, item.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
