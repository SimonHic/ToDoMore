package ie.setu.todomore.ui.screens.taskedit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.todomore.data.TodoModel
import ie.setu.todomore.firebase.services.AuthService
import ie.setu.todomore.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val taskId: String = checkNotNull(savedStateHandle["taskId"])
    var task = mutableStateOf<TodoModel?>(null)
    var isError = mutableStateOf(false)
    var errorState = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            try {
                isLoading.value = true
                task.value = repository.get(authService.email!!, taskId)
                isError.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isError.value = true
                errorState.value = e
                isLoading.value = false
            }
        }
    }

    fun updateTask(updated: TodoModel, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.update(authService.email!!, updated)
                isError.value = false
                isLoading.value = false
                onSuccess()
            } catch (e: Exception) {
                isError.value = true
                errorState.value = e
                isLoading.value = false
            }
        }
    }
}