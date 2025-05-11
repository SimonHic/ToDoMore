package ie.setu.todomore.ui.screens.tasklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.todomore.data.TodoModel
import ie.setu.todomore.data.Priority
//import ie.setu.todomore.data.TodoJSONStore
import ie.setu.todomore.ui.components.general.TopAppBarProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

/*val sampleTasks = mutableStateListOf(
    TodoModel(id = 1, title = "Buy Games", priority = Priority.HIGH),
    TodoModel(id = 2, title = "Finish assignment", priority = Priority.HIGH),
    TodoModel(id = 3, title = "Call Mother", priority = Priority.MEDIUM),
    TodoModel(id = 4, title = "Workout", priority = Priority.MEDIUM),
    TodoModel(id = 5, title = "Read a book", priority = Priority.LOW),
)*/

// Screen to showcase all tasks added
@Composable
fun TaskListScreen(navController: NavController){
    val context = LocalContext.current
    val viewModel: TaskListViewModel = hiltViewModel()
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    // Everytime Tasklist screen loads, get all tasks
    LaunchedEffect(true) {
        if(!currentUserEmail.isNullOrBlank()){
            viewModel.getTasks()
        }
    }
    val allTasks = viewModel.uiTasks.collectAsState().value
    val focusMode = viewModel.isFocusMode.value
    val tasks = if(focusMode){
        allTasks.filter { it.priority == Priority.HIGH }
    } else{
        allTasks
    }
    val isClearButtonEnabled = tasks.any { it.markForDel }

    Column(modifier = Modifier.padding(16.dp)) {

        TopAppBarProvider(
            navController = navController,
            currentScreen = ie.setu.todomore.navigation.TaskList,
            canNavigateBack = false,
            email = FirebaseAuth.getInstance().currentUser?.email?:"",
            name = FirebaseAuth.getInstance().currentUser?.displayName?:"",
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ){
            Icon(
                imageVector = Icons.Filled.FlashOn,
                contentDescription = "Focus Mode Icon",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Focus Mode", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = viewModel.isFocusMode.value,
                onCheckedChange = {viewModel.isFocusMode.value = it},
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.inversePrimary,
                    checkedThumbColor = MaterialTheme.colorScheme.tertiary)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            val message = if(focusMode){"No High-Priority tasks to focus on."
            } else{
                "No tasks available"
            }
            Text(text = message, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 20.dp))
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                tasks.forEach { task ->
                    TaskItem(
                        task = task,
                        onClick = { navController.navigate("taskEdit/${task._id}") },
                        onLongClick = {
                            viewModel.toggleMarkForDeletion(task._id)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Clear and Remove Completed Tasks Button
        Button(
            onClick = {
                viewModel.clearMarkedTasks()},
            enabled = isClearButtonEnabled,

            modifier = Modifier.fillMaxWidth(),

            // Only enabled if tasks are marked, defaulted to false
            //enabled = tasks.any {it.markForDel}
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