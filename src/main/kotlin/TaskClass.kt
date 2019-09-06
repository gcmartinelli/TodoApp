/*  A data class representing a task in the ToDo app.

    The class is instantiated with the following parameters:
        * title : String -> A short title for the task
        * description : String -> A short description
        * dueDate : Date -> When the task is due

    Custom methods include:
        * updateStatus (Returns a boolean representing if
                        the task is past its due date)
        * complete (Marks the task status as complete)
        * delete (Deletes the task)
 */

import java.util.Date

data class Task(
            val id: Int,
            val creationDate: Date = Date(),
            var dueDate: Date,
            val title: String,
            val description: String = "",
            var status: String = "todo") {

    fun updateStatus() : Boolean {
        /* Returns a boolean representing
            if the task is past it's due date */
        return Date().after(dueDate)
    }

    fun complete(){
        /* Marks the task status as complete */
        TODO ()
    }

    fun delete(){
        /* Deletes the task */
        TODO ()
    }

}