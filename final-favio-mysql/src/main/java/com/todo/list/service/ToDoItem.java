package com.todo.list.service;


import com.todo.list.implementation.Status;

import java.util.Date;

public interface ToDoItem {
    int getId();

    void setId(int id);

    String getItemDetails();

    void setItemDetails(String description);

    Date getDueDate();

    void setDueDate(Date dueDate);

    Status getStatus();

    void setStatus(Status status);
}


