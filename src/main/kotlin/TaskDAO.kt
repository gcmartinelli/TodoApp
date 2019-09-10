/*
    Data Access Object for working with Tasks.
    Interfaces between the data store and is used by the app views.

    Methods:
    * addTask -> receives a dueDate:Date, title:String and desc:String. Saves task to DB
    * delTask -> given a taskID flags the task as deleted
    * completeTask -> given a taskID flags the task as complete
    
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

    fun completeTask(taskID: Int) {
        /* Given a task ID, flags a task as completed */
        val task = tasks[taskID] ?: throw IllegalArgumentException("[ERROR] completeTask: Task with ID {$taskID} not found.")
        task.status = "complete"
        tasks.replace(taskID, task)
    }

}