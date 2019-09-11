/* Runs the To-do App API server */

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import java.util.Date
import java.util.Timer
import kotlin.concurrent.schedule

fun main() {
    val taskDAO = TaskDAO()

    val app = Javalin.create().apply {
        config.addStaticFiles("/materialize")
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found")}
    }.start(8000)

    app.routes {
        /* where the magic happens */
        ApiBuilder.get("/tasks") { ctx ->
            val tasks = taskDAO.tasks.filter{ !it.value.deleted }.values.sortedBy { it.dueDate } // Let's ignore deleted tasks ofc
            /* The block below transforms the date fields from unix time to strings.
                There probably is a more concise way of doing this. And I could be doing this in
                a dedicated 'views' file instead of mixing this logic with the API logic. But given the
                small scope of the project I'll keep this as is for now.
             */
            class RenderTaskClass(val id:Int, val creationDate:String, val dueDate:String,
                             val title:String, val desc:String, val status:String){} // Temp class
            val tasksToRender = mutableListOf<RenderTaskClass>() // Instantiate an empty list
            for (task in tasks) { // Iterate over the filtered tasks, transforming the date values
                tasksToRender.add(
                    RenderTaskClass(
                        id = task.id,
                        creationDate = Date(task.creationDate).toString(),
                        dueDate = Date(task.dueDate).toString(),
                        title = task.title, desc = task.description,
                        status = task.status
                    )
                )
            }
           ctx.render("tasks.mustache",  mapOf("tasks" to tasksToRender))
        }

        ApiBuilder.post("/tasks/add") { ctx ->
            // A lot of repetition in this block... need to refactor
            val dueDate = ctx.formParam("dueDate") ?: throw IllegalArgumentException("[ERROR] DueDate cannot be Null")
            val title = ctx.formParam("title") ?: throw IllegalArgumentException("[ERROR] Title cannot be Null")
            val desc = ctx.formParam("desc")
            try {
                if (desc != null){
                    taskDAO.addTask(dueDate = dueDate, title = title, desc = desc)
                } else {
                    taskDAO.addTask(dueDate, title)}
                ctx.status(200)
                ctx.redirect("/tasks")
            } catch (e: IllegalArgumentException) {
                ctx.status(500)
            }
        }

        ApiBuilder.post("/tasks/delete/:taskID") { ctx ->
            val taskID = ctx.pathParam("taskID").toInt()
            taskDAO.delTask(taskID).also{ println("[DELETED] Task with ID $taskID set to deleted.")}
            ctx.status(200)
            ctx.redirect("/tasks")
        }

        ApiBuilder.post("/tasks/complete/:taskID") { ctx ->
            val taskID = ctx.pathParam("taskID").toInt()
            taskDAO.changeStatus(taskID, "complete").also{ println("[UPDATED] Task with ID $taskID status set to complete.")}
            ctx.status(200)
            ctx.redirect("/tasks")
        }
    }

    // This runs asynchronously every 'period' (in milliseconds).
    Timer("checkTaskStatus").schedule(delay=0, period=60000) {
        taskDAO.checkStatuses()
    }
}