package ie.setu.todomore.ui.screens.taskcreate

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.todomore.data.TodoModel
import ie.setu.todomore.firebase.services.AuthService
import ie.setu.todomore.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskCreateViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService)
    : ViewModel() {
    var isError = mutableStateOf(false)
    var errorState = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    fun insert(task: TodoModel, onSuccess: () -> Unit) =
        viewModelScope.launch {
            try {
                // Check specific email for firebase insert
                Log.d("DEBUG_FIREBASE", "Inserting task for email: ${authService.email}")
                isLoading.value = true
                repository.insert(authService.email!!,task)
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