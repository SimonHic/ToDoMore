package ie.setu.todomore.ui.screens.taskedit

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ie.setu.todomore.data.Priority
import ie.setu.todomore.data.TodoModel
import com.google.firebase.auth.FirebaseAuth
import ie.setu.todomore.ui.components.general.TopAppBarProvider
import ie.setu.todomore.navigation.TaskEdit

// Screen to allow users to edit created tasks
@Composable
fun TaskEditScreen(navController: NavController) {
    //Text(text = "Task Edit Screen - Editing Task: $taskId")
    val context = LocalContext.current
    //val todoStore = remember { TodoJSONStore(context) }
    val viewModel: TaskEditViewModel = hiltViewModel()
    val task = viewModel.task.value
    //val task = todoStore.findById(taskId)
    val navigateUp: () -> Unit = { navController.navigateUp() }

    if (task == null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Error: Task not found!!", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("tasklist") }) {
                Text("Go Back")
            }
        }
        return
    }
    // Fields to edit
    var title by remember { mutableStateOf(task.title) }
    var selectedPriority by remember { mutableStateOf(task.priority) }

    Scaffold(
        topBar = {
            TopAppBarProvider(
                navController = navController,
                currentScreen = TaskEdit,
                canNavigateBack = true,
                email = FirebaseAuth.getInstance().currentUser?.email ?: "",
                name = FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                navigateUp = navigateUp
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Edit Task", style = MaterialTheme.typography.headlineMedium)

            // Task title input field
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Task Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Priority Handling
            Text(text = "Select Priority Level:")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //Priority.values().forEach { priority ->
                Button(
                    onClick = {
                        selectedPriority = Priority.LOW
                        Toast.makeText(context, "Priority set to Low", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedPriority == Priority.LOW) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("Low")
                }

                Button(
                    onClick = {
                        selectedPriority = Priority.MEDIUM
                        Toast.makeText(context, "Priority set to Medium", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedPriority == Priority.MEDIUM) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("Medium")
                }

                Button(
                    onClick = {
                        selectedPriority = Priority.HIGH
                        Toast.makeText(context, "Priority set to High", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedPriority == Priority.HIGH) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("High")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))

            // Update and Save Task Button
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.updateTask(
                            task.copy(
                                title = title,
                                priority = selectedPriority
                            )
                        ) {
                            navController.navigate("tasklist")
                        }
                        /*todoStore.update(taskId, title, selectedPriority)
                    navController.navigate("tasklist")*/
                    } else {
                        Toast.makeText(context, "Please enter a task title!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) {
                Text("Update Task")
            }
        }
    }
}