package com.todo.list.implementation;

import com.todo.list.service.ToDoItem;
import java.util.Date;

public class BasicToDoItem implements ToDoItem {
    private int id; // Unique identifier
    private String description;
    private Date dueDate;
    private Status status;

    public BasicToDoItem() {
        this.status = Status.NOT_STARTED; // Default status
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getItemDetails() {
        return description;
    }

    @Override
    public void setItemDetails(String description) {
        this.description = description;
    }

    @Override
    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
