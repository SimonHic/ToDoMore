package ie.setu.todomore.ui.screens.streaks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.todomore.firebase.services.AuthService
import ie.setu.todomore.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StreakData(
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastCompletedDate: String? = null,
    val totalClearedTasks: Int = 0
)

@HiltViewModel
class StreaksViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService: AuthService
) : ViewModel() {

    private val _streakState = MutableStateFlow(StreakData())
    val streakState: StateFlow<StreakData> = _streakState.asStateFlow()

    fun getStreakData() {
        val userEmail = authService.email
        if(userEmail.isNullOrBlank()){
            Log.w("DEBUG_STREAK", "Email is null, skipping streak fetch")
            return
        }

        viewModelScope.launch {
            try {
                val result = repository.getStreakData(userEmail)
                _streakState.value = result
            } catch (e: Exception) {
                Log.d("DEBUG_STREAK", "Error fetching streak data", e)
            }
        }
    }
}