// TEMP DISABLE DUE TO FIRESTORE MIGRATION
/*
package ie.setu.todomore.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
import java.util.Random

const val JSON_FILE = "todos.json"

val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
val listType: Type = object : TypeToken<ArrayList<TodoModel>>(){}.type

fun generateRandomId(): Long{
    return Random().nextLong()
}

class TodoJSONStore(private val context: Context) {
    var todos: MutableList<TodoModel> = mutableListOf()
    private val file: File by lazy { File(context.filesDir, JSON_FILE) }

    init {
        if (file.exists()) {
            deserialize()
        }
    }

    fun findAll(): MutableList<TodoModel> {
        return todos.sortedBy { it.priority.value }.toMutableList()
    }

    fun findById(id: Long): TodoModel? {
        return todos.find { it.id == id }
    }

    fun create(todo: TodoModel) {
        todo.id = generateRandomId()
        todos.add(todo)
        serialize()
    }

    fun update(taskId: Long, newTitle: String, newPriority: Priority) {
        val taskIndex = todos.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            todos[taskIndex] = todos[taskIndex].copy(title = newTitle, priority = newPriority)
            serialize()
        }
    }

    fun toggleTaskDeletion(taskId: Long){
        val taskIndex = todos.indexOfFirst { it.id == taskId }
        if (taskIndex != -1){
            todos[taskIndex] = todos[taskIndex].copy(markForDel = !todos[taskIndex].markForDel)
            serialize()
        }
    }

    fun deleteMarked() {
        todos.removeAll { it.markForDel }
        serialize()
    }

    private fun serialize() {
        file.writeText(gsonBuilder.toJson(todos, listType))
    }

    private fun deserialize() {
        if (file.exists()) {
            val jsonString = file.readText()
            todos = gsonBuilder.fromJson(jsonString, listType) ?: mutableListOf()
        } else {
            todos = mutableListOf()
        }
    }
}

*/