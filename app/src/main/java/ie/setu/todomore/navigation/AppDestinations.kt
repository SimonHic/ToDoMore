package ie.setu.todomore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument


interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object TaskList : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "Task List"
    override val route = "tasklist"
}

object TaskCreate : AppDestination {
    override val icon = Icons.Filled.AddCircle
    override val label = "Create Task"
    override val route = "taskcreate"
}

object About : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "About"
    override val route = "about"
}

object TaskEdit : AppDestination {
    override val icon = Icons.Filled.Details
    override val label = "Edit Task"
    const val idArg = "id"
    override val route = "taskedit/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType }
    )
}

val bottomAppBarDestinations = listOf(TaskCreate, TaskList, About)
val allDestinations = listOf(TaskList, TaskCreate, About, TaskEdit)