-- Create the database
CREATE DATABASE IF NOT EXISTS ToDoListDB;

-- Select the newly created database
USE ToDoListDB;

-- Create a table in the database
CREATE TABLE IF NOT EXISTS ToDoItems
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    description TEXT,
    dueDate     DATETIME,
    status      ENUM ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') NOT NULL DEFAULT 'NOT_STARTED'
);
