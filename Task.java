package com.mycompany.todolist_assignment;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private final int id;
    String title,description,dueDate,category,priorityLvl, recurrenceInterval;
    private boolean isComplete;
    
    Task(String title,String description, String dueDate, String category,String priorityLvl,boolean isComplete, String interval) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priorityLvl = priorityLvl;
        this.id = idCounter.getAndIncrement(); //increments the id number everytime a new task is created
        this.isComplete = false;
        this.recurrenceInterval = interval;
    }

    // GETTERS
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

    public boolean isComplete() {
        return isComplete;
    }

    public void markComplete() {
        this.isComplete = true;
    }

    public String getRecurrenceInterval() {
        return recurrenceInterval;
    }

    //SETTERS

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDependencyId(int dependencyId) {
        this.dependencyId = dependencyId;
    }

    public void setRecurrenceInterval(String interval) {
        this.recurrenceInterval = interval;
    }


    
    @Override
    public String toString() {
        return (isComplete ? "[Completed]" : "[Incomplete]") + "\n" +
               "ID: " + id + "\n" +
               "Task Title: " + title + "\n" +
               "Description: " + description + "\n" +
               "Due Date: " + dueDate + "\n" +
               "Category: " + category + "\n" +
               "Priority Level: " + priorityLvl + "\n";
               "Status: + (isComplete ? "Completed" : "Incomplete") + "\n" +
                (recurrenceInterval != null ? "Recurrence: " + recurrenceInterval : "");
    }
}
