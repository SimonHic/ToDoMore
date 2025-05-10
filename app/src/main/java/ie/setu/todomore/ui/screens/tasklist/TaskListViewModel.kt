package ie.setu.todomore.ui.screens.tasklist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.todomore.data.TodoModel
import ie.setu.todomore.firebase.services.AuthService
import ie.setu.todomore.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService: AuthService
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TodoModel>>(emptyList())
    val uiTasks: StateFlow<List<TodoModel>> = _tasks.asStateFlow()

    var isError = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var errorState = mutableStateOf(Exception())
    private val _markedForDeletion = mutableListOf<String>()
    val markedTasks: List<String> get() =_markedForDeletion

    init {
        getTasks()
    }

    fun getTasks() {
        val userEmail = authService.email
        if(userEmail.isNullOrBlank()){
            Log.w("DEBUG_FIREBASE", "Email is null, skipping task fetch")
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true
                Log.d("DEBUG_FIREBASE", "Fetching task for email: $userEmail")
                repository.getAll(userEmail).collect { items ->
                    _tasks.value = items.map{it.copy(markForDel = _markedForDeletion.contains(it._id))
                    }
                    /*_tasks.value = items*/
                    isError.value = false
                    //isLoading.value = false
                }
            } catch (e: Exception) {
                isError.value = true
                //isLoading.value = false
                errorState.value = e
            } finally {
                isLoading.value = false
            }
        }
    }

    fun toggleMarkForDeletion(taskId: String){
        if(_markedForDeletion.contains(taskId)){
            _markedForDeletion.remove(taskId)
        } else{
            _markedForDeletion.add(taskId)
        }
        getTasks()
    }

    fun clearMarkedTasks(){
        viewModelScope.launch {
            markedTasks.forEach {taskId ->
                repository.delete(authService.email!!, taskId)
            }
            _markedForDeletion.clear()
            getTasks()
        }
    }

    /*fun deleteTask(task: TodoModel) {
        viewModelScope.launch {
            repository.delete(authService.email!!, task._id)
            getTasks() // Refresh the  list after deletion
        }
    }*/
}