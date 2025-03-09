package ie.setu.todomore.ui.screens.taskedit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Screen to allow users to edit created tasks
@Composable
fun TaskEditScreen(modifier: Modifier = Modifier, taskId: Long){
    Text(text = "Task Edit Screen - Editing Task: $taskId")
}