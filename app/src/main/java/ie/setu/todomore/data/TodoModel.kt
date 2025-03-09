package ie.setu.todomore.data

data class TodoModel (
    var id: Long = 0,
    var title: String = "",
    var markForDel: Boolean = false, // Default value is false because you don't want to delete something you just made
    var priority: Priority = Priority.MEDIUM)

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