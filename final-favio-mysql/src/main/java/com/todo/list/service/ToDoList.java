package com.todo.list.service;

public interface ToDoList {
    void createList();

    void addItem(ToDoItem item);

    void updateStatus(ToDoItem item);

    void displayList();

    void removeItem(ToDoItem item);

    //method to get ToDoItem at a specific index
    ToDoItem getToDoItemAt(int index);
}