/*
    Data Access Object for working with Tasks.
    Interfaces between the data store and is used by the app views.
 */
import java.util.Date

class TaskDAO(){
    // I'll initially work with data stored in memory.
    // For convenience I'll initialize some here
    val tasks = hashMapOf(
        0 to Task(0, Date(), Date(2019-1900, 9, 21), title="A simple task", description="Things to do", status="todo"),
        1 to Task(1, Date(), Date(2019-1900, 8, 9), title="Buy eggs", description="Can't make omeletes", status="todo"),
        2 to Task(2, Date(), Date(2019-1900, 8, 5), title="Learn Kotlin", description="Cool jobs require it ;)", status="late"),
        3 to Task(3, Date(), Date(2019-1900, 10, 21), title="Buy bitcoin", description="Buy the dip and HODL", status="todo"),
        4 to Task(4, Date(), Date(2019-1900, 8, 8), title="Water plants", description="", status="todo"),
        5 to Task(5, Date(), Date(2019-1900, 11, 20), title="Get Xmas gifts", description="Don't leave it to the last minute", status="todo")
    )

    fun getTasks() : List<Task> {
        TODO()
    }

    fun addTask() {
        TODO()
    }

    fun delTask() {
        TODO()
    }

    fun completeTask() {
        TODO()
    }

}