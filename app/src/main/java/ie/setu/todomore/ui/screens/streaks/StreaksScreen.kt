package ie.setu.todomore.ui.screens.streaks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Alignment
import ie.setu.todomore.ui.theme.Purple80

// Screen to showcase streaks
@Composable
fun StreaksScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: StreaksViewModel = hiltViewModel()
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    val streak = viewModel.streakState.collectAsState().value

    // Everytime Tasklist screen loads, get all tasks
    LaunchedEffect(true) {
        if (!currentUserEmail.isNullOrBlank()) {
            viewModel.getStreakData()
        }
    }

    Scaffold(
        topBar = {
            TopAppBarProvider(
                navController = navController,
                currentScreen = ie.setu.todomore.navigation.Streaks,
                canNavigateBack = true,
                email = FirebaseAuth.getInstance().currentUser?.email ?: "",
                name = FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                navigateUp = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Purple80, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Icon(
                        imageVector = Icons.Filled.LocalFireDepartment,
                        contentDescription = "Streak Fire Icon",
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Total Tasks Completed: ${streak.totalClearedTasks}")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Current Streak: ${streak.currentStreak}")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Longest Streak: ${streak.longestStreak}")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Last Streak Completed: ${streak.lastCompletedDate ?: "N/A"}")
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}