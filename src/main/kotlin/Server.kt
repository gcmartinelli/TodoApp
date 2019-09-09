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
            ctx.json(taskDAO.tasks)
        }

        ApiBuilder.post("/tasks/add") { ctx ->
            val task = ctx.body<Task>()
            taskDAO.addTask(dueDate = task.dueDate,
                            title = task.title,
                            desc = task.description)
            ctx.status(201)
        }

        ApiBuilder.delete("/tasks/delete/:taskID") { ctx ->
            taskDAO.delTask(ctx.pathParam("taskID").toInt())
            ctx.status(204)
        }

        ApiBuilder.patch("/tasks/complete/:taskID") { ctx ->
            taskDAO.completeTask(ctx.pathParam("taskID").toInt())
            ctx.status(204)
        }
    }

}

