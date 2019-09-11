import java.util.*
import kotlin.collections.HashMap

fun main(){
    val DAO = TaskDAO()

    fun printTasks(tasks : HashMap<Int, Task>){
        for (task in tasks){
            println(task)
        }
        println("---------")
    }

    printTasks(DAO.tasks)

    val newTask = DAO.addTask(dueDate = "2019-09-23", title = "A new task")
    printTasks(DAO.tasks)

    DAO.delTask(2)
    printTasks(DAO.tasks)

    val newTask2 = DAO.addTask(dueDate = "2019-09-08", title = "A new task after a delete")
    printTasks(DAO.tasks)
}