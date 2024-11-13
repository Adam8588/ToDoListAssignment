package com.mycompany.todolist_assignment;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private final int id;
    String title,description,dueDate,category,priorityLvl;
    
    Task(String title,String description, String dueDate, String category,String priorityLvl) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priorityLvl = priorityLvl;
        this.id = idCounter.getAndIncrement(); //increments the id number everytime a new task is created
    }
    
    public int getId() { //method that returns the id when called
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getDueDate() {
        return dueDate;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getPriorityLvl() {
        return priorityLvl;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Task Title: " + title + "\n" +
               "Description: " + description + "\n" +
               "Due Date: " + dueDate + "\n" +
               "Category: " + category + "\n" +
               "Priority Level: " + priorityLvl + "\n";
    }
}
