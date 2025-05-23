package ie.setu.todomore.firebase.services

import android.net.Uri
import ie.setu.todomore.data.TodoModel
import kotlinx.coroutines.flow.Flow
import ie.setu.todomore.ui.screens.streaks.StreakData

typealias Task = TodoModel
typealias Tasks = Flow<List<Task>>

interface FirestoreService {

    suspend fun getAll(email: String) : Tasks
    suspend fun get(email: String, taskId: String) : Task?
    suspend fun insert(email: String, task: Task)
    suspend fun update(email: String, task: Task)
    suspend fun delete(email: String, taskId: String)
    //suspend fun updatePhotoUris(email: String, uri: Uri)
    suspend fun getStreakData(email: String): StreakData
    suspend fun updateStreakOnClear(email: String, completedTaskCount: Int)
}