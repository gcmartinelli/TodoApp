/*
    Data Access Object for working with Tasks.
    Interfaces between the data store and is used by the app views.

    Methods:
    * addTask -> receives a dueDate:Date, title:String and desc:String. Saves task to DB
    * delTask -> given a taskID flags the task as deleted
    * completeTask -> given a taskID flags the task as complete
    
 */
import java.util.Date

class TaskDAO(){
    // I'll initially work with data stored in memory.
    // For convenience I'll initialize some here
    var tasks = hashMapOf(
        0 to Task(0, Date(), Date(2019-1900, 9, 21), title="A simple task", description="Things to do", status="todo"),
        1 to Task(1, Date(), Date(2019-1900, 8, 9), title="Buy eggs", description="Can't make omeletes", status="todo"),
        2 to Task(2, Date(), Date(2019-1900, 8, 5), title="Learn Kotlin", description="Cool jobs require it ;)", status="late"),
        3 to Task(3, Date(), Date(2019-1900, 10, 21), title="Buy bitcoin", description="Buy the dip and HODL", status="todo"),
        4 to Task(4, Date(), Date(2019-1900, 8, 8), title="Water plants", description="", status="todo"),
        5 to Task(5, Date(), Date(2019-1900, 11, 20), title="Get Xmas gifts", description="Don't leave it to the last minute", status="todo")
    )

    fun addTask(dueDate : Date,
                title : String,
                desc : String = "") {
        /* Adds a new task to the database if the due date is valid,
            else returns an error */

        // Check if dueDate is valid (i.e. not in the past).
        // This should be done client side, but it's good to practice error handling
        val currDate = Date()
        if (currDate.after(dueDate)) {
            val message = "[ERROR] We can't go back in time :( This due date is already gone"
            println(message) // TODO: Implement logging
            throw IllegalArgumentException(message)
        } else {
            val nextID : Int = tasks.size
            val newTask = Task(id = nextID, dueDate = dueDate, title = title, description = desc)
            tasks.put(nextID, newTask)
        }

    }

    fun delTask(taskID : Int) {
        /* Given a task ID, flags a task as deleted */
        val task = tasks.get(taskID) ?: throw IllegalArgumentException("[ERROR] Task with ID {$taskID} not found.")
        task.deleted = true
        tasks.replace(taskID, task)
    }

    fun completeTask() {
        TODO()
    }

}