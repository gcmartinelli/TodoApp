import java.util.Date

data class Task(val creationDate: Date = Date(),
           var dueDate: Date,
           val title: String,
           val description: String = "",
           var status: String) {

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