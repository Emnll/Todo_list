import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

/*Objective: Implement a TODO list application
* - Use the backlog made in trello to guide myself
* - Implement the basic CRUD of tasks first
* - Test each functionality
* - Start creating lists
* - There should be a way to filter by priority
* - Use the terminal to display actions
* - Every time a new task is created the order should be resorted
* - Do not use frameworks
*
* Optional requirements:
*  - Check the number of tasks by status
*  - Update a task
*  - Filter by date
*  - Save the dada in a file
*
* */
public class Main {
    public static void main(String[] args) {
        int listChoice;

        Scanner sc = new Scanner(System.in);
        ListController listController = new ListController();

        do{
            System.out.println("Welcome to the To-Do list Manager!" +
                    "\nPlease make Your choice:" +
                    "\nList Management:" +
                    "\n[1] Create New List" +
                    "\n[2] Read Available Lists" +
                    "\n[3] Access a List and Manage Tasks in it" +
                    "\n[4] Delete a List" +
                    "\n[0] Exit Program" +
                    "\nYour Choice: ");
            listChoice = getValidInteger(sc, 0, 4);


            switch (listChoice){
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                case 1:
                    System.out.println("Choose a Name for the New List: ");
                    String nameNewList = sc.next();
                    listController.createList(nameNewList);
                    break;
                case 2:
                    listController.printLists();
                    break;
                case 3:
                    System.out.println("Choose a Name for the List you want to access: ");
                    String nameChosen = sc.next();
                    if(listController.chooseList(nameChosen)){
                        EditTasksInList(nameChosen, listController);
                    } else {
                        System.out.println("This list does not exist or the name you entered is incorrect.");
                    }
                    break;
                case 4:
                    System.out.println("Choose a Name for the List you want to delete: ");
                    String nameChosen2 = sc.next();
                    if(listController.chooseList(nameChosen2)){
                        listController.deleteList(nameChosen2);
                    } else{
                        System.out.println("This list does not exist or the name you entered is incorrect.");
                    }
                    break;
                default:
                    System.out.println("This number is invalid. Please try again.");
                    break;
            }
        } while (listChoice != 0);

        sc.close();
    }

    public static void EditTasksInList(String listName, ListController listController){
        int taskChoice;
        String taskName;
        String taskDescription;
        int taskPriority;
        String taskCategory;


        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("You are currently in " + listName +
                    ".\nPlease choose an option: " +
                    "\nTask Management:" +
                    "\n[1] Create New Task" +
                    "\n[2] Read Tasks in " + listName +
                    "\n[3] Change Task Status" +
                    "\n[4] Delete a Task" +
                    "\n[5] List by Category, Priority or Status" +
                    "\n[0] Exit List" +
                    "\nYour Choice: ");

            taskChoice = sc.nextInt();

            switch (taskChoice){
                case 1:
                    System.out.println("Choose a Name for the New Task: ");
                    taskName = sc.next();
                    System.out.println("Enter a Description for the New Task: ");
                    taskDescription = sc.next();
                    System.out.println("Enter a Priority for the New Task (1-5): ");
                    taskPriority = getValidInteger(sc, 1, 5);
                    System.out.println("Enter a Category for the New Task: ");
                    taskCategory = sc.next();
                    Task newTask = new Task(taskName, taskDescription, taskPriority, taskCategory);
                    listController.addTask(newTask);
                    break;
                case 2:
                    List<Task> tasks = listController.getTasks();
                    if (tasks == null) {
                        System.out.println("No tasks in the list.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }
                    break;
                case 3:
                    List<Task> tasksChange = listController.getTasks();
                    if(tasksChange.isEmpty()){
                        System.out.println("No tasks in the list.");
                        break;
                    }
                    System.out.println("Choose the number of the Task: ");
                    int changeStatus = getValidInteger(sc, 1, listController.getTasks().size()) - 1;
                    Task chosenTask = tasksChange.get(changeStatus);
                    chosenTask.setDate(LocalDate.now());
                    System.out.println("Choose the number of the Status of the Task:" +
                            "\n [1] To-do" +
                            "\n [2] Doing" +
                            "\n [3] Done");
                    int taskStatusNumber = getValidInteger(sc, 1, 3);

                    switch (taskStatusNumber){
                        case 1:
                           listController.changeStatus(chosenTask,"todo" );
                           break;
                        case 2:
                            listController.changeStatus(chosenTask,"doing" );
                            break;
                        case 3:
                            listController.changeStatus(chosenTask,"done");
                            break;
                        default:
                            System.out.println("This number is invalid. Please try again.");
                            break;
                    }
                    break;
                case 4:
                    List<Task> tasksDel = listController.getTasks();
                    if(tasksDel.isEmpty()){
                        System.out.println("No tasks in the list.");
                        break;
                    }
                    System.out.println("Choose the number of the Task: ");
                    int deleteStatus = getValidInteger(sc, 1, listController.getTasks().size()) - 1;

                    Task DelTask = tasksDel.get(deleteStatus);
                    listController.deleteTask(DelTask, tasksDel);
                    System.out.println("Task Deleted.");
                    break;
                case 5:
                    System.out.println("Choose the type you want to list the tasks by:" +
                            "\n [1] Priority" +
                            "\n [2] Category" +
                            "\n [3] Status");
                    int typeChoice = getValidInteger(sc, 1, 3);
                    switch (typeChoice){
                        case 1:
                            listController.listBy("priority");
                            break;
                        case 2:
                            listController.listBy("category");
                            break;
                        case 3:
                            listController.listBy("status");
                            break;
                        default:
                            System.out.println("This number is invalid. Please try again.");
                    }

                default:
                    System.out.println("This number is invalid. Please try again.");



            }
        } while (taskChoice != 0);
    }

    public static int getValidInteger(Scanner sc, int min, int max) {
        int input = 1;
        boolean isValid = false;

        while (!isValid) {
            try {
                input = sc.nextInt();
                sc.nextLine();

                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }

        return input;
    }
}