/*
    Data Access Object for working with Tasks.
    Interfaces between the data store and is used by the app views.

    Methods:
    * addTask -> receives a dueDate:Date, title:String and desc:String. Saves task to DB
    * delTask -> given a taskID flags the task as deleted
    * changeStatus -> given a taskID and a status string, change the task's status
    * checkStatuses -> checks statuses of all non-late tasks and changes their status if they are overdue
    
 */
import java.util.Date
import java.text.SimpleDateFormat

class TaskDAO(){
    // I'll initially work with data stored in memory.
    // For convenience I'll initialize some here
    var tasks = hashMapOf(
        0 to Task(0, dueDate = 1568099780979, title="A simple task", description="Things to do", status="todo"),
        1 to Task(1, dueDate = 1568049780979, title="Buy eggs", description="Can't make omeletes", status="todo"),
        2 to Task(2, dueDate = 1568058780979, title="Learn Kotlin", description="Cool jobs require it ;)", status="late"),
        3 to Task(3, dueDate = 1568068780979, title="Buy bitcoin", description="Buy the dip and HODL", status="todo"),
        4 to Task(4, dueDate = 1568078780979, title="Water plants", description="Water plants", status="todo"),
        5 to Task(5, dueDate = 1568088780979, title="Get Xmas gifts", description="Don't leave it to the last minute", status="todo")
    )

    fun addTask(dueDate : String,
                title : String,
                desc : String = "") {
        /* Adds a new task to the database if the due date is valid,
            else returns an error */

        // Check if dueDate is valid (i.e. not in the past).
        // This should be done client side, but it's good to practice error handling
        val dueDateUnixTime = SimpleDateFormat("yyyy-MM-dd").parse(dueDate).time
        val currDate = Date().time
        if (currDate > dueDateUnixTime) {
            val message = "[ERROR] We can't go back in time :( This due date is already gone"
            println(message) // TODO: Implement logging
            throw IllegalArgumentException(message)
        } else {
            val nextID : Int = tasks.size
            val newTask = Task(id = nextID, dueDate = dueDateUnixTime, title = title, description = desc)
            tasks[nextID] = newTask
        }

    }

    fun delTask(taskID : Int) {
        /* Given a task ID, flags a task as deleted */
        val task = tasks[taskID] ?: throw IllegalArgumentException("[ERROR] delTask: Task with ID {$taskID} not found.")
        task.deleted = true
        tasks.replace(taskID, task)
    }

    fun  changeStatus(taskID : Int, status : String) {
        /* Given a task ID and a status string, changes the task's status.
        *   Status must be one of: "todo", "complete", "late" */
        val task = tasks[taskID] ?: throw IllegalArgumentException("[ERROR] completeTask: Task with ID {$taskID} not found.")
        when(status){
            "todo" -> task.status = "todo"
            "complete" -> task.status = "complete"
            "late" -> task.status = "late"
            else -> throw java.lang.IllegalArgumentException("[ERROR] changeStatus: Illegal status argument $status. Only one of 'todo|complete|late' accepted.")
            }
        tasks.replace(taskID, task)
    }

    fun checkStatuses() {
        /* Checks status of all tasks that are "todo",
           if they are late, changes their status. */
        println("[JOB] Checking statuses...")
        val currUnixTime = Date().time
        for (task in tasks.values.filter { it.status == "todo" }) {
            if (task.dueDate < currUnixTime){
                changeStatus(task.id, "late")
                println("[JOB] Task ${task.id} is late. Changing status.")
            }
        }
    }
}
