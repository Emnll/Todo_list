/* A list holds a set of tasks
  - The lists will be files
  - ListController makes all the manipulation in these files
*/

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListController {
    private File[] lists;
    private final String pathToList = System.getProperty("user.dir") + "/src/resources/";
    private Path directoryPath = Paths.get(pathToList);
    private File directory;
    private String fileName;

    public ListController() {
    if(!Files.exists(directoryPath)){
        try{
            Files.createDirectory(directoryPath);
        } catch(Exception e){
            System.err.println("Failed to create directory: " + e.getMessage());
            return;
        }


    }
        directory = new File(pathToList);
        updateLists();
    }

    public void updateLists(){
        lists = directory.listFiles();
        Arrays.sort(lists);
    }

    public void printLists(){
        System.out.println("Lists: " + directory);
        if(lists != null){
            System.out.println("\n======================\n");
            System.out.println("Lists of tasks that can be used:\n");
            for(File file : lists){
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
            System.out.println("\n======================\n");

        } else{
            System.out.println("You haven't created any List yet!");
        }
    }

    public void createList(String name){
        if (!name.endsWith(".txt")) {
            name = name + ".txt";
        }
        System.out.println("Creating list: " + name);
        Path path = Paths.get(directoryPath.toString(), name);
        try {
            Files.createFile(path);
            updateLists();
            System.out.println("Created the file: " + path.toString());
        } catch(Exception e){
            System.err.println("Failed to create List: " + e.getMessage());
        }

    }

    public boolean chooseList(String name){
        if (!name.endsWith(".txt")) {
            name = name + ".txt";
        }
        for (File file : lists) {
            //The method .contains could be used in order to get a partial match
            if (file.getName().equals(name)) {
                fileName = pathToList + name;
                return true;
            }
        }
        return false;
    }

    public List<Task> getTasks(){
        List<Task> tasks = new ArrayList<>();
        // FileInputStream -> Input Stream that reads a File
        try(FileInputStream fileIn = new FileInputStream(fileName)){
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            while(true){
                try{
                    Task task = (Task) objectIn.readObject();
                    tasks.add(task);
                } catch(java.io.EOFException e){
                    break;
                } catch (Exception e){
                    System.err.println("An error occurred: " + e.getMessage());
                }
            }

        } catch(Exception e){
            System.err.println("Error reading tasks from file ");
            return tasks;
        }
        return tasks;
    }

    public void listBy(String type) {
        List<Task> tasks = getTasks();
        if(tasks == null){
            System.err.println("The list is Empty!");
            return;
        }

        Map<String, List<Task>> groupedTasks = tasks.stream()
                .collect(Collectors.groupingBy(task -> {
                    switch (type) {
                        case "priority":
                            return String.valueOf(task.getPriority());
                        case "category":
                            return task.getCategory().toString();
                        case "status":
                            return task.getStatus().toString();
                        default:
                            return "Uncategorized";
                    }
                }));

        groupedTasks.forEach((group, taskList) -> {
            System.out.println("------- * " + group + " * -------");
            taskList.forEach(System.out::println);
            System.out.println();
        });
    }

    public void addTask(Task task){
        // FileOutputStream -> Output Stream that writes in a File
        List<Task> tasks = getTasks();
        tasks.add(task);
        saveTasksToFile(tasks);
    }

    public void deleteList(String name){
        if (!name.endsWith(".txt")) {
            name += ".txt";
        }
        for (File file : lists) {
            if (file.getName().equals(name)) {
                if(file.delete()){
                    lists = directory.listFiles();
                    System.out.println("Deleted the file: " + file.getName());
                    break;
                }
            }
        }

    }

    public void changeStatus(Task task, String status){
        List<Task> tasks = getTasks();
        for (Task task2 : tasks) {
            if (task2.getName().equals(task.getName())) {
                tasks.remove(task2);
                task.setStatus(status);
                tasks.add(task);
                saveTasksToFile(tasks);
                break;
            }
        }
        saveTasksToFile(tasks);
    }

    public void deleteTask(Task task, List<Task> tasks){
        for (Task task2 : tasks) {
            if (task.equals(task2)) {
                tasks.remove(task2);
                saveTasksToFile(tasks);
                break;
            }
        }


    }

    private void saveTasksToFile(List<Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            Collections.sort(tasks);
            for(Task task : tasks){
                oos.writeObject(task);
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

}
