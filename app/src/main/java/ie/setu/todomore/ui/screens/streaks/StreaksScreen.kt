package ie.setu.todomore.ui.screens.streaks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.todomore.ui.components.general.TopAppBarProvider
import androidx.compose.runtime.LaunchedEffect

// Screen to showcase streaks
@Composable
fun StreaksScreen(navController: NavController){
    val context = LocalContext.current
    val viewModel: StreaksViewModel = hiltViewModel()
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    val streak = viewModel.streakState.collectAsState().value

    // Everytime Tasklist screen loads, get all tasks
    LaunchedEffect(true) {
        if(!currentUserEmail.isNullOrBlank()){
            viewModel.getStreakData()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        TopAppBarProvider(
            navController = navController,
            currentScreen = ie.setu.todomore.navigation.Streaks,
            canNavigateBack = true,
            email = FirebaseAuth.getInstance().currentUser?.email?:"",
            name = FirebaseAuth.getInstance().currentUser?.displayName?:"",
            navigateUp = {navController.popBackStack()}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total Tasks Completed: ${streak.totalClearedTasks}")
        Text(text = "Current Streak: ${streak.currentStreak}")
        Text(text = "Longest Streak: ${streak.longestStreak}")
        Text(text = "Last Streak Completed: ${streak.lastCompletedDate ?: "N/A"}")
    }
}