/* A task should have the following properties:
* - Name
* - Description
* - Finishing date
* - Priority Level (1 - 5)
* - Category
* - Status (ToDo, Doing and Done)
*
* A task should have the basic CRUD
* - Create
* - Read
* - Update
* - Delete
*
*
* */
import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable, Comparable<Task> {
    private String Name;
    private String Description;
    private LocalDate Date;
    private int Priority;
    private String Category;
    private String Status;


    public Task(String name, String description, int priority, String category) {
        Name = name;
        Description = description;
        Priority = priority;
        Category = category;
        Status = "todo";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String toString() {

        return "[" + Status + "] " + "Name: " + Name + "\nDescription: " + Description + "\nFinishing Date: "
                + Date + "\nPriority: " + Priority
                + "\nCategory: " + Category;
    }

    @Override
    public int compareTo(Task other){
        return Integer.compare(this.getPriority(), other.getPriority());
    }
}
