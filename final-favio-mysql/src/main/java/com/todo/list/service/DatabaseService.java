package com.todo.list.service;

import java.util.List;

public interface DatabaseService {
    ToDoItem addTask(ToDoItem item);

    List<ToDoItem> getToDoList();
    public boolean updateTask(ToDoItem updatedItem);
    boolean removeTask(ToDoItem item);
}
