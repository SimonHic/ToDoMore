package ie.setu.todomore.ui.screens.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ie.setu.todomore.data.TodoModel
import ie.setu.todomore.data.Priority

val sampleTasks = mutableStateListOf(
    TodoModel(id = 1, title = "Buy Games", priority = Priority.HIGH),
    TodoModel(id = 2, title = "Finish assignment", priority = Priority.HIGH),
    TodoModel(id = 3, title = "Call Mother", priority = Priority.MEDIUM),
    TodoModel(id = 4, title = "Workout", priority = Priority.MEDIUM),
    TodoModel(id = 5, title = "Read a book", priority = Priority.LOW),
)

// Screen to showcase all tasks added
@Composable
fun TaskListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
){
    //Text(text = "Task List Screen")
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Task List", style = MaterialTheme.typography.headlineMedium)

        if (sampleTasks.isEmpty()) {
            Text(text = "No tasks available", modifier = Modifier.padding(top = 20.dp))
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                sampleTasks.forEach { task ->
                    TaskItem(task = task, onClick = { navController.navigate("taskEdit/${task.id}") })
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: TodoModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Priority: ${task.priority}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    val fakeNavController = androidx.navigation.compose.rememberNavController()
    TaskListScreen(navController = fakeNavController)
}