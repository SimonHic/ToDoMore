package ie.setu.todomore.ui.screens.about

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Screen to give a brief explanation of the app
@Composable
fun AboutScreen(modifier: Modifier = Modifier){
    Text(text = "ToDo More is a task management app that allows users to add, edit, and organise tasks with priority levels. MORE COMING SOON!")
}