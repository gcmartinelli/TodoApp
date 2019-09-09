/* Runs the To-do App API server */

import TaskDAO
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder

fun main() {
    val taskDAO = TaskDAO()

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found")}
    }.start(8000)

    app.routes {
        /* where the magic happens */
        ApiBuilder.get("/tasks") { ctx ->
            val tasks = taskDAO.tasks.filter{ it.value.deleted == false } // Let's ignore deleted tasks ofc
            ctx.json(tasks)
        }

        ApiBuilder.post("/tasks/add") { ctx ->
            val dueDate = ctx.formParam("dueDate")?.toLong() ?: throw IllegalArgumentException("[ERROR] DueDate cannot be Null")
            val title = ctx.formParam("title") ?: throw IllegalArgumentException("[ERROR] Title cannot be Null")
            val desc = ctx.formParam("desc")
            try {
                if (desc != null){
                    taskDAO.addTask(dueDate = dueDate, title = title, desc = desc)
                } else {
                    taskDAO.addTask(dueDate, title)}
                ctx.status(200)
            } catch (e: IllegalArgumentException) {
                ctx.status(500)
            }
        }

        ApiBuilder.delete("/tasks/delete/:taskID") { ctx ->
            val taskID = ctx.pathParam("taskID").toInt()
            taskDAO.delTask(taskID).also{ println("[DELETED] Task with ID $taskID set to deleted.")}

            ctx.status(200)
        }

        ApiBuilder.patch("/tasks/complete/:taskID") { ctx ->
            val taskID = ctx.pathParam("taskID").toInt()
            taskDAO.completeTask(taskID).also{ println("[UPDATED] Task with ID $taskID status set to complete.")}
            ctx.status(200)
        }
    }

}

