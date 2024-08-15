package com.todo.list;


import com.todo.list.database.DatabaseInitializer;
import com.todo.list.implementation.BasicToDoItem;
import com.todo.list.implementation.BasicToDoList;
import com.todo.list.service.ToDoItem;
import com.todo.list.service.ToDoList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TodolistProject {
    public static void main(String[] args) {
        DatabaseInitializer.executeSqlScript();

        ToDoList toDoList = new BasicToDoList();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            try {
                System.out.println("------ ToDoList Menu ------");
                System.out.println("1. Display ToDo List");
                System.out.println("2. Add Task");
                System.out.println("3. Update Task Status");
                System.out.println("4. Remove Task");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        toDoList.displayList();
                        break;
                    case 2:
                        addTask(toDoList, scanner);
                        break;
                    case 3:
                        updateTaskStatus(toDoList, scanner);
                        break;
                    case 4:
                        removeTask(toDoList, scanner);
                        break;
                    case 0:
                        System.out.println("Exiting ToDoList. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric choice.");
                scanner.nextLine(); // Clear the buffer
                choice = -1; // Set an invalid choice to continue the loop
            }
        } while (choice != 0);
    }

    private static void addTask(ToDoList toDoList, Scanner scanner) {
        displayAndAddItem(toDoList, scanner);

    }

    private static void updateTaskStatus(ToDoList toDoList, Scanner scanner) {
        toDoList.displayList();
        System.out.print("Enter the task ID to update status: ");
        int taskIndex = scanner.nextInt();
        scanner.nextLine();

        List<ToDoItem> tasks = ((BasicToDoList) toDoList).getTodoItems();
        if (taskIndex >= 0) {

            tasks.stream().forEach(task -> {
                if (task.getId() == taskIndex) {
                    toDoList.updateStatus(task);
                }
            });

        } else {
            System.out.println("Invalid task ID. No task updated.");
            return;
        }
    }

    /**
     * @param toDoList
     * @param scanner
     */
    private static void removeTask(ToDoList toDoList, Scanner scanner) {
        toDoList.displayList();
        System.out.print("Enter the task ID to remove: ");
        int taskIndex = scanner.nextInt();
        scanner.nextLine();

        List<ToDoItem> tasks = ((BasicToDoList) toDoList).getTodoItems();
        if (taskIndex >= 0) {

            tasks.stream().forEach(task -> {
                if (task.getId() == taskIndex) {
                    toDoList.removeItem(task);
                }
            });
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Invalid task ID. No task removed.");
            return;
        }
    }


    /**
     * @param toDoList
     * @param scanner
     */
    private static void displayAndAddItem(ToDoList toDoList, Scanner scanner) {
        toDoList.displayList();

        ToDoItem newItem = new BasicToDoItem();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        newItem.setItemDetails(description);

        boolean validDate = false;
        while (!validDate) {
            System.out.print("Enter due date (yyyy-MM-dd): ");
            String dueDateString = scanner.nextLine();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                Date dueDate = dateFormat.parse(dueDateString);
                newItem.setDueDate(dueDate);
                validDate = true; // Break out of the loop if the date is parsed successfully
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
                System.out.print("Do you want to set the due date to today? (yes/no): ");
                String setToday = scanner.nextLine().toLowerCase();
                if ("yes".equals(setToday)) {
                    newItem.setDueDate(new Date());
                    validDate = true; // Break out of the loop
                } else {
                    System.out.println("Task not added. Returning to the menu.");
                    return; // Return to the menu without adding the task
                }
            }
        }

        toDoList.addItem(newItem);

    }
}

