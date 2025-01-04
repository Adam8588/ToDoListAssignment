package com.mycompany.todolist_assignment;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.InputMismatchException;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.*;

public class ToDoList_Assignment {

    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        
        ArrayList<Task> listOfTasks = new ArrayList<>(); //Creates a new ArrayList to store the tasks
        
        System.out.println("Welcome to your To-Do List!");
        System.out.println("Before starting, please enter your email address for task notifications:");
        String userEmail = input.nextLine();
        System.out.println();
        
        while (true) {
            int choice = getChoice(input);
            
            switch (choice) {
                case 1 -> taskAdder(input, listOfTasks);           
                case 2 -> displayTasks(listOfTasks);
                case 3 -> findTask(input, listOfTasks);
                case 4 -> fullTextSearch(input, listOfTasks);
                case 5 -> deleteTask(input, listOfTasks);
                case 6 -> markTaskComplete(input, listOfTasks);
                case 7 -> checkAndSendNotifications(userEmail, listOfTasks);
                case 8 -> editTask(input, listOfTasks); 
                case 9 -> sortTask(input, listOfTasks);
                case 10 -> recurringTask(input, listOfTasks);
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
            (4) Search tasks by keyword
            (5) Delete task
            (6) Mark Task as Complete
            (7) Send Notifications for Tasks Due in 24 Hours
            (8) Edit a task
            (9) Sort tasks
            (10) Add a recurring task
            (0) Exit
            ==========================""");
            return input.nextInt();
    }
    
    //TASK ADDER
    private static Task taskAdder(Scanner input, ArrayList<Task> listOfTasks) { //uses the ArrayList as a parameter, enabling taskAdder to access the list
        input.nextLine(); //clear the newline
        String title,description,dueDate,category,priorityLvl,interval;
        
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
        
        // Input recurrence interval
        System.out.print("Enter recurrence interval (e.g., Daily, Weekly, Monthly, or leave blank if none): ");
        interval = input.nextLine();
        if (interval.isEmpty()) {
            interval = null; // Set null if no interval is provided
        }
        
        Task task = new Task(title, description, dueDate, category, priorityLvl, false, interval);
        task.setLoadState(Task.LoadState.LOADED);
        listOfTasks.add(task);
        System.out.println("Task \"" + title + "\" added successfully!");

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
        int id;
        System.out.println("What is the ID of the task?");
        while (true) 
        {
            try {
                
                id = scanner.nextInt(); 
                scanner.nextLine(); 
                break; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer ID.");
                scanner.nextLine(); 
            }
        }
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

    //TASK FINDER BY TITLE OR DESCRIPTION
    private static void fullTextSearch(Scanner input, ArrayList<Task> listOfTasks) {
        System.out.print("Enter a keyword to search by title or description: ");
        input.nextLine();
        String keyword = input.nextLine().toLowerCase();
        boolean found = false;
        Iterator<Task> iterator = listOfTasks.iterator();
    
        System.out.println("Searching for tasks matching the keyword \"" + keyword + "\":");
        System.out.println("\n=== Search Results ===");
        while (iterator.hasNext()) 
        {
            Task task = iterator.next();
            if (task.getTitle().toLowerCase().contains(keyword) || task.getDescription().toLowerCase().contains(keyword)) 
            {
                System.out.println(task);
                found = true;
            }   
        }
        if (!found) {
            System.out.println("No tasks found matching the keyword \"" + keyword + "\".");
            System.out.println();
        }
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
    
    //MARK TASK AS COMPLETE
    public static void markTaskComplete(Scanner input , ArrayList<Task> listOfTasks) {
        Iterator<Task> iterator = listOfTasks.iterator();
        System.out.println("Which task would you like to mark as complete? (Enter ID)");
        int id = input.nextInt();
        boolean found = false;
        while (iterator.hasNext()) 
        {
            Task task = iterator.next();
            if (task.getId() == id) {
                if (!areDependenciesComplete(task, listOfTasks)) {
                    System.out.println("Cannot complete task. Dependencies are not met.");
                    return;
                }
                task.markComplete();
                System.out.println("Task \"" + task.getTitle() + "\" marked as completed");
                found = true;
                break;
            }
        }
        if(!found) {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    //CHECK FOR TASK THAT DUE WITHIN 24 HOURS
    private static boolean isTaskDueWithin24Hours(String dueDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            Date taskDate = dateFormat.parse(dueDate);
            Date currentDate = new Date();
            long difference = taskDate.getTime() - currentDate.getTime();
            return difference > 0 && difference <= 24 * 60 * 60 * 1000; // Within 24 hours
        } catch (ParseException e) {
            return false;
        }
    }

    // EDITING TASKS
    private static void editTask(Scanner input, ArrayList<Task> listOfTasks) {
        System.out.print("Enter task ID to edit: ");
        int id = input.nextInt();
        Task task = findTaskById(listOfTasks, id);
        if (task != null) {
            System.out.println("What would you like to edit?\n1. Title\n2. Description\n3. Due Date\n4. Category\n5. Priority\n6. Set Task Dependency\n7. Cancel");
            int edit = input.nextInt();
            input.nextLine();
            switch (edit) {
                case 1 -> {
                    System.out.print("Enter new title: ");
                    task.setTitle(input.nextLine());
                }
                case 2 -> {
                    System.out.print("Enter new description: ");
                    task.setDescription(input.nextLine());
                }
                case 3 -> {
                    System.out.print("Enter new due date (DD-MM-YYYY): ");
                    task.setDueDate(input.nextLine());
                }
                case 4 -> {
                    System.out.print("Enter new category: ");
                    task.setCategory(input.nextLine());
                }
                case 5 -> {
                    System.out.print("Enter new priority: ");
                    task.setPriority(input.nextLine());
                }
                case 6 -> setTaskDependency(input, listOfTasks);
                case 7 -> System.out.println("Edit cancelled");
                default -> System.out.println("Invalid choice");
            }
            System.out.println("Task is updated");
        } else {
            System.out.println("Task is not found");
        }
    }

    private static void setTaskDependency(Scanner input, ArrayList<Task> listOfTasks) {
    System.out.print("Enter the ID of the task to set dependency for: ");
    int taskId = input.nextInt();
    Task task = findTaskById(listOfTasks, taskId);

    if (task == null) {
        System.out.println("Task not found!");
        return;
    }

    System.out.print("Enter the ID of the task it depends on: ");
    int dependencyId = input.nextInt();
    Task dependencyTask = findTaskById(listOfTasks, dependencyId);

    if (dependencyTask == null) {
        System.out.println("Dependency task not found!");
        return;
    }

    task.setDependencyId(dependencyId);
    System.out.println("Dependency set successfully: Task " + taskId + " depends on Task " + dependencyId);
}

    private static boolean areDependenciesComplete(Task task, ArrayList<Task> listOfTasks) {
        if (task.getDependencyId() == -1) {
            return true; // No dependencies
        }

        Task dependencyTask = findTaskById(listOfTasks, task.getDependencyId());
        return dependencyTask != null && dependencyTask.isComplete();
    }

    // TASK SORTING
    private static void sortTask(Scanner input, ArrayList<Task> listOfTasks) {
        System.out.println("Sort by:\n1. Due Date (Ascending)\n2. Due Date (Descending)\n3. Priority (High to Low)\n4. Priority (Low to High)");
        int sortChoice = input.nextInt();
        switch (sortChoice) {
            case 1 -> listOfTasks.sort(Comparator.comparing(Task::getDueDate));
            case 2 -> listOfTasks.sort(Comparator.comparing(Task::getDueDate).reversed());
            case 3 -> listOfTasks.sort(Comparator.comparing(Task::getPriorityLvl).reversed());
            case 4 -> listOfTasks.sort(Comparator.comparing(Task::getPriorityLvl));
            default -> System.out.println("Invalid choice.");
        }
        System.out.println("Tasks sorted successfully!");
    }

    //RECURRING TASKS
    private static void recurringTask(Scanner input, ArrayList<Task> listOfTasks) {
        Task task = taskAdder(input, listOfTasks);
        System.out.print("Enter recurrence interval (daily, weekly, monthly): ");
        String interval = input.nextLine();
        task.setRecurrenceInterval(interval);
        System.out.println("Recurring task \"" + task.getTitle() + "\" created successfully!");
    }

    //SEND EMAIL NOTIFICATION
    private static void sendEmail(String userEmail, Task task) {
        String host = "smtp.gmail.com"; 
        String from = "yiwenlai0502@gmail.com"; 
        String password = "yhvx clmn qhdz ansa"; 

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Task Reminder: " + task.getTitle());
            message.setText("Hello,\n\nThis is a friendly reminder that the task \"" + task.getTitle() 
                            + "\" is due within the next 24 hours.\n\nTask Details:\n"
                            + "Description: " + task.getDescription() + "\n"
                            + "Due Date: " + task.getDueDate() + "\n\n"
                            + "Please ensure to complete it on time.\n\nThank you!");

            Transport.send(message);
            System.out.println("Reminder email sent successfully to: " + userEmail + " for task \"" + task.getTitle() + "\" due in 24 hours.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    //CHECK AND SEND NOTIFICATIONS
    private static void checkAndSendNotifications(String userEmail, ArrayList<Task> listOfTasks) {
        boolean emailSent = false;
        for (Task task : listOfTasks) 
        {
            if (isTaskDueWithin24Hours(task.getDueDate())) 
            {
                sendEmail(userEmail, task);
                emailSent = true;
            }
        }
        if (!emailSent) 
        {
            System.out.println("No tasks are due within the next 24 hours.");
        }
    }
}