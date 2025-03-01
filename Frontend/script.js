var tasks = []

const taskName = document.getElementById('task-name');
const taskCategory = document.getElementById('task-category');
const taskDescription = document.getElementById('task-description');
const taskDeadline = document.getElementById('task-deadline');
const taskPriority = document.getElementById('task-priority');

const updateTaskName = document.getElementById('update-task-name');
const updateTaskCategory = document.getElementById('update-task-category');
const updateTaskDescription = document.getElementById('update-task-description');
const updateTaskDeadline = document.getElementById('update-task-deadline');
const updateTaskPriority = document.getElementById('update-task-priority');
const updateTaskStatus = document.getElementById('update-task-status');

const openUpdateModal = document.getElementById('update');

const createTaskBtn = document.getElementById('createTaskBtn');
const deleteTaskBtn = document.getElementById('deleteTaskBtn');
const updateTaskBtn = document.getElementById('updateTaskBtn');

const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
const deleteModal = new bootstrap.Modal(document.getElementById('deleteTask'));
const updateModal = new bootstrap.Modal(document.getElementById('updateTask'))

let taskToDelete = null;
let taskToUpdate = null;

function setTable(){
    var table = document.getElementById("table-body")
    table.innerHTML = '';

    tasks.forEach((task, index) =>{
        const row = document.createElement('tr');
        row.innerHTML = `<tr>
                                <td>${task.Status}</td>
                                <td>${task.Name}</td>
                                <td>${task.Priority}</td>
                                <td>${task.Category}</td>
                                <td>${task.Description}</td>
                                <td>${task.Date}</td>
                                <td>
                                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" 
                                    data-bs-target="#deleteTask" data-task-id = "${index}">Delete Task</button>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-success" data-bs-toggle="modal" 
                                    data-bs-target="#updateTask" data-task-id = "${index}">Update Task</button>
                                </td>
                                
                         </tr>`
        table.appendChild(row);
    })

}

createTaskBtn.addEventListener('click', () => {
    if(taskName.value !== '' && taskCategory.value !== '' && taskDescription.value !== '' && taskDeadline.value !== '' && taskPriority.value !== 'Select the Priority') {
        addTask({Name: taskName.value, Description: taskDescription.value, Date: taskDeadline.value, Priority: taskPriority.value, Category: taskCategory.value, Status: 'To-Do'})
        setTable(tasks)
        taskName.value = ''
        taskCategory.value = ''
        taskDescription.value = ''
        taskDeadline.value = ''
        taskPriority.value = 'Select the Priority'
        modal.hide()
    } else {
        alert('Please fill the entire form')
    }
});

document.addEventListener('click', (event) => {
    if (event.target.classList.contains('btn-danger')) {
        taskToDelete = event.target.getAttribute('data-task-id');
    }
});

document.addEventListener('click', (event) => {
    if (event.target.classList.contains('btn-success')) {
        const taskIndex = event.target.getAttribute('data-task-id');
        setUpdateModal(taskIndex);
    }
});

deleteTaskBtn.addEventListener('click', () => {
    if (taskToDelete !== null) {
        deleteTask(taskToDelete);
        taskToDelete = null;
        setTable(tasks);
        deleteModal.hide();
    }
});

updateTaskBtn.addEventListener('click', () => {
    console.log('Update')
    if(taskToUpdate !== null){
        const task = tasks[taskToUpdate]
        if(updateTaskName.value !== task.Name || updateTaskDescription.value !== task.Description || updateTaskCategory.value !== task.Category ||
            updateTaskDeadline.value !== task.Date || updateTaskPriority.value !== task.Priority || updateTaskStatus.value !== task.Status){
            task.Name = updateTaskName.value;
            task.Date = updateTaskDeadline.value;
            task.Category = updateTaskCategory.value;
            task.Priority = updateTaskPriority.value;
            task.Description = updateTaskDescription.value;
            task.Status = updateTaskStatus.value;
            updateTask(task, taskToUpdate);
            setTable(tasks)
            taskToUpdate = null;
            updateModal.hide()
        } else{
            alert('Make sure you actually change something it')
        }

    }
})

function setUpdateModal(index) {
    const task = tasks[index];
    document.getElementById('update-task-name').value = task.Name;
    document.getElementById('update-task-category').value = task.Category;
    document.getElementById('update-task-description').value = task.Description;
    document.getElementById('update-task-deadline').value = task.Date;
    document.getElementById('update-task-priority').value = task.Priority;
    document.getElementById('update-task-status').value = task.Status;
    taskToUpdate = index;
}

document.getElementById('update').addEventListener('click', (event) => {
    console.log(openUpdateModal)
        const taskIndex = event.target.getAttribute('data-task-id');
        setUpdateModal(taskIndex);
});

function addTask(task){
    tasks.push(task)
}

function deleteTask(index){
    tasks.splice(index,1);
}

function updateTask(task, index){
    tasks[index] = task;
}



const exampleModal = document.getElementById('exampleModal')
if (exampleModal) {
    exampleModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget
        const recipient = button.getAttribute('data-bs-whatever')
        const modalTitle = exampleModal.querySelector('.modal-title')
        const modalBodyInput = exampleModal.querySelector('.modal-body input')

        modalTitle.textContent = `New message to ${recipient}`
        modalBodyInput.value = recipient
    })
}

/* <td>
                                    <button onclick="deleteTask(${index})">Delete</button>
                                    <button onclick="editTask(${index})">Edit</button>
                                </td>
                                */