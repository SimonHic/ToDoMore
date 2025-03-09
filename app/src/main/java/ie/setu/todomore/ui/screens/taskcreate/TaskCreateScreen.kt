package ie.setu.todomore.ui.screens.taskcreate

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ie.setu.todomore.data.Priority
import ie.setu.todomore.data.TodoModel
import ie.setu.todomore.ui.screens.tasklist.sampleTasks

// Screen to allow suers to create tasks
@Composable
fun TaskCreateScreen(navController: NavController){
    //Text(text = "Task Create Screen")
    var title by remember {mutableStateOf("")}
    var selectedPriority by remember { mutableStateOf(Priority.MEDIUM)}
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)){
        Text(text = "Create New Task", style = MaterialTheme.typography.headlineMedium)

        // Task title input
        OutlinedTextField(
            value = title,
            onValueChange = {title = it},
            label = {Text("Task Title")},
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(16.dp))

        // Priority Handling
        Text(text = "Select Priority Level:")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Priority.values().forEach { priority ->
                Button(
                    onClick = {
                        selectedPriority = priority
                        Toast.makeText(context, "Priority set to ${priority.name}", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedPriority == priority) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(text = priority.toString())
                }
            }}
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotBlank()){
                    addTask(title, selectedPriority)
                    navController.navigate("tasklist")
                } else{
                    Toast.makeText(context, "Please enter a task title!!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank()
        ){
            Text("Create Task")
        }
    }
}

fun addTask(title: String, priority: Priority){
    val newTask = TodoModel(id = (sampleTasks.size +1).toLong(), title = title, priority = priority)
    sampleTasks.add(newTask)
}