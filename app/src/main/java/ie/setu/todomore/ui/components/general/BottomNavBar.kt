package ie.setu.todomore.ui.components.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


@Composable
fun BottomNavBar(navController: NavController){
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
    ){
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = "Create Task", tint = Color.White)},
            label = {Text("Create", color = Color.White)},
            selected = false,
            onClick = {navController.navigate("taskCreate")}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Checklist, contentDescription = "Task List", tint = Color.White)},
            label = {Text("Tasks", color = Color.White)},
            selected = false,
            onClick = {navController.navigate("taskList")}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Timelapse, contentDescription = "Streak", tint = Color.White)},
            label = {Text("Streaks", color = Color.White)},
            selected = false,
            onClick = {navController.navigate("streaks")}
        )
    }
}