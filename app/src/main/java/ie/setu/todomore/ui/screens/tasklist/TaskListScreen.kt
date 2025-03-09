package ie.setu.todomore.ui.screens.tasklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
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
                    TaskItem(
                        task = task,
                        onClick = { navController.navigate("taskEdit/${task.id}") },
                        onLongClick = {toggleTaskDeletion(task.id)}
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Clear and Remove Completed Tasks Button
        Button(
            onClick = {clearCompletedTasks()},
            modifier = Modifier.fillMaxWidth(),

            // Only enabled if tasks are marked, defaulted to false
            enabled = sampleTasks.any {it.markForDel}
        ){
            Text("Clear Completed")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(task: TodoModel, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = MaterialTheme.shapes.medium
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (task.markForDel) TextDecoration.LineThrough else TextDecoration.None
            )
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

// Toggle the 'markForDel' task parameter to true or false (default is false on creation)
fun toggleTaskDeletion(taskId: Long){
    val taskIndex = sampleTasks.indexOfFirst { it.id == taskId }
    if (taskIndex != -1){
        val task = sampleTasks[taskIndex]
        sampleTasks[taskIndex] = task.copy(markForDel = !task.markForDel)
    }
}

// Remove all completed tasks marked for deletion
fun clearCompletedTasks(){
    sampleTasks.removeAll {it.markForDel}
}