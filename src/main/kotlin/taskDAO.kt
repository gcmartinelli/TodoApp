/*
    Data Access Object for working with Tasks.
    Interfaces between the data store and is used by the app views.
 */
import java.util.Date

class TaskDAO(){
    // I'll initially work with data stored in memory.
    // For convenience I'll initialize some here
    var tasks = mutableListOf(
        Task(0, Date(), Date(2019-1900, 9, 21), title="A simple task", description="Things to do", status="todo"),
        Task(1, Date(), Date(2019-1900, 8, 9), title="Buy eggs", description="Can't make omeletes", status="todo"),
        Task(2, Date(), Date(2019-1900, 8, 5), title="Learn Kotlin", description="Cool jobs require it ;)", status="late"),
        Task(3, Date(), Date(2019-1900, 10, 21), title="Buy bitcoin", description="Buy the dip and HODL", status="todo"),
        Task(4, Date(), Date(2019-1900, 8, 8), title="Water plants", description="", status="todo"),
        Task(5, Date(), Date(2019-1900, 11, 20), title="Get Xmas gifts", description="Don't leave it to the last minute", status="todo")
    )

    fun addTask(dueDate : Date,
                title : String,
                desc : String = "") {
        /* Adds a new task to the database if the due date is valid,
            else returns an error */
        // Check if dueDate is valid (i.e. not in the past)
        if (Date().after(dueDate)) {
            val message : String = "[ERROR] We can't go back in time :( This due date is already gone"
            println(message) // TODO: Implement logging
        } else {
            val nextID : Int = tasks.size
            val newTask = Task(id = nextID, dueDate = dueDate, title = title, description = desc)
            tasks.add(newTask)
        }

    }

    fun delTask() {
        TODO()
    }

    fun completeTask() {
        TODO()
    }

}