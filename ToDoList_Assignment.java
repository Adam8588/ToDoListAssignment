package com.mycompany.todolist_assignment;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ToDoList_Assignment {

    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        
        ArrayList<Task> listOfTasks = new ArrayList<>(); //Creates a new ArrayList to store the tasks
        
        System.out.println("Welcome to your To-Do List!");
        
        while (true) {
            int choice = getChoice(input);
            
            switch (choice) {
                case 1 -> taskAdder(input, listOfTasks);           
                case 2 -> displayTasks(listOfTasks);
                case 3 -> findTask(input, listOfTasks);                 
                case 4 -> deleteTask(input, listOfTasks);
                case 0 -> {
                    System.out.println("Goodbye!");
                    input.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    //USER CHOICE
    private static int getChoice(Scanner input) {
        System.out.println("""
            ==========================
            What would you like to do?
            (1) Add a task
            (2) Output all tasks
            (3) Find a task by ID
            (4) Delete task
            (0) Exit
            ==========================""");
            return input.nextInt();
    }
    
    //TASK ADDER
    private static Task taskAdder(Scanner input, ArrayList<Task> listOfTasks) { //uses the ArrayList as a parameter, enabling taskAdder to access the list
        input.nextLine(); //clear the newline
        String title,description,dueDate,category,priorityLvl;
        
        //input title
        System.out.print("Enter task title: ");
        title = input.nextLine();
        //input description
        System.out.print("Enter task description: ");
        description = input.nextLine();
        //input dueDate
        while (true) {
            System.out.print("Enter due date (DD-MM-YYYY): ");
            dueDate = input.nextLine();
            if (dateChecker(dueDate)) {
                break;
            } else {
                System.out.println("Invalid date. Please enter a valid date in the format DD-MM-YYYY");
            }
        }
        //input category
        while (true) {
            System.out.print("Enter task category (Homework, Personal, Work): ");
            category= input.nextLine();
            category = category.substring(0, 1).toUpperCase() + category.substring(1);
            if (categoryChecker(category)) {
                break;
            } else {
                System.out.println("Invalid category. Please choose one of the three");
            }
        }
        //input priority
        while (true) {
            System.out.print("Priority level (Low, Medium, High): ");
            priorityLvl = input.nextLine();
            priorityLvl = priorityLvl.substring(0, 1).toUpperCase() + priorityLvl.substring(1);
            if (priorityChecker(priorityLvl)) {
                break;
            } else {
                System.out.println("Invalid priority level. Please choose one of the three");
            }
        }
        
        Task task = new Task(title,description,dueDate,category,priorityLvl); //creates a new Task object
        listOfTasks.add(task); //adds the new object into the ArrayList
        System.out.println("Task added successfully!");
        
        return task;
    }
    
    //DATE CHECKER FOR TASKADDER
    private static boolean dateChecker(String dueDate) {
        String datePattern = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); //disables lenient parsing
        
        if (dueDate.matches(datePattern)) {
            try {
                dateFormat.parse(dueDate);
            } catch (ParseException e) {
                return false;
            }
        }
        
        return dueDate.matches(datePattern);
    }
    
    //CATEGORY CHECKER FOR TASKADDER
    private static boolean categoryChecker(String category) {
        return category.matches("Homework|Personal|Work");
    }
    
    //PRIORITY CHECKER FOR TASKADDER
    private static boolean priorityChecker(String priority) {
        return priority.matches("Low|Medium|High");
    }
    
    //TASK DISPLAYER
    private static void displayTasks(ArrayList<Task> listOfTasks) {
        if (listOfTasks.isEmpty()) {
            System.out.println("There's nothing here!");
        }
        for (Task task : listOfTasks) { //iterates through the ArrayList for each element in it
             System.out.println(task);
        }
    }
    
    //TASK FINDER
    private static void findTask(Scanner scanner, ArrayList<Task> listOfTasks) {
        System.out.println("What is the ID of the task?");
        int id = scanner.nextInt();
        Task task = findTaskById(listOfTasks, id);
        if (task == null) {
            System.out.println("There is no task with this ID!");
        } else {
            System.out.println(task);
        }
    }
    
    //TASK FINDER BY ID
    private static Task findTaskById(ArrayList<Task> listOfTasks, int id) {
        for (Task task : listOfTasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    
    //TASK DELETER
    private static void deleteTask(Scanner input, ArrayList<Task> listOfTasks) {
        String title;
        Iterator<Task> iterator = listOfTasks.iterator();
        System.out.println("Which task would you like to delete? (Enter ID)");
        int id = input.nextInt();
        boolean found = false;
        
        while (iterator.hasNext()) {
            Task task = iterator.next();
            
            if (task.getId() == id) {
                title = task.getTitle();
                iterator.remove();
                System.out.println("Task \"" + title + "\" deleted successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Task with ID " + id + " not found.");
        }
    }
    
    //TASK SORTER
    
}
