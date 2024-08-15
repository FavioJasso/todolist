package com.todo.list.implementation;

import com.todo.list.database.ToDoListMySQLAdapter;
import com.todo.list.service.ToDoItem;
import com.todo.list.service.ToDoList;

import java.util.List;
import java.util.Scanner;

public class BasicToDoList implements ToDoList {
    private ToDoListMySQLAdapter adapter;

    // Constructor
    public BasicToDoList() {
        this.adapter = new ToDoListMySQLAdapter();
    }

    @Override
    public void createList() {
        System.out.println("New ToDo list initialized (Database operations might not clear existing items).");
    }

    @Override
    public void addItem(ToDoItem item) {
        ToDoItem addedItem = adapter.addTask(item);
        if (addedItem != null) {
            System.out.println("ToDoItem added to the database.");
            printToDoItem(1,item);
        } else {
            System.out.println("Failed to add ToDoItem to the database.");
        }
    }


    public List<ToDoItem> getTodoItems() {
        return adapter.getToDoList();
    }

    @Override
    public void updateStatus(ToDoItem item) {
        System.out.println("Enter the new status for the task (e.g., NOT_STARTED, IN_PROGRESS, COMPLETED): ");
        Scanner scanner = new Scanner(System.in);
        String statusInput = scanner.nextLine();

        try {
            Status newStatus = Status.valueOf(statusInput.toUpperCase());
            item.setStatus(newStatus);
            boolean updated = adapter.updateTask(item);
            if (updated) {
                System.out.println("Status updated successfully in the database.");

                printToDoItem(1,item);

            } else {
                System.out.println("Failed to update status in the database.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status input. Please enter a valid status.");
        }
    }

    @Override
    public void displayList() {
        List<ToDoItem> items = adapter.getToDoList();
        if (items.isEmpty()) {
            System.out.println("ToDo list is empty.");
        } else {
            System.out.println("ToDo list:");
            for (int i = 0; i < items.size(); i++) {
                ToDoItem item = items.get(i);
                printToDoItem(i+1,item);

            }
        }
    }

    @Override
    public void removeItem(ToDoItem item) {
        boolean removed = adapter.removeTask(item);
        if (removed) {
            System.out.println("ToDoItem removed from the database.");
        } else {
            System.out.println("Failed to remove ToDoItem from the database.");
        }
    }

    @Override
    public ToDoItem getToDoItemAt(int index) {
        List<ToDoItem> items = adapter.getToDoList();
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        } else {
            System.out.println("Invalid index. Returning null.");
            return null;
        }
    }

    private void printToDoItem(int index,ToDoItem item) {
        System.out.println(index+")" + "  ID:"+ item.getId() + " : " + item.getItemDetails() +
                " - Due: " + item.getDueDate() +
                " - Status: " + item.getStatus());
    }
}
