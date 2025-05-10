package ie.setu.todomore.data

import com.google.firebase.firestore.DocumentId

data class TodoModel (
    //var id: Long = 0,
    @DocumentId var _id: String = "",
    var title: String = "",
    var markForDel: Boolean = false, // Default value is false because you don't want to delete something you just made
    var priority: Priority = Priority.MEDIUM,
    var userEmail: String = ""
)

enum class Priority(val value: Int){
    HIGH(1),   // Highest Priority
    MEDIUM(2), // Medium Priority
    LOW(3);     // Lowest Priority

    override fun toString(): String = when(this) {
        HIGH -> "High"
        MEDIUM -> "Medium"
        LOW -> "Low"
    }
}