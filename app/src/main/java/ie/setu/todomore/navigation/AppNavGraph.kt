package ie.setu.todomore.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.todomore.ui.screens.about.AboutScreen
import ie.setu.todomore.ui.screens.taskcreate.TaskCreateScreen
import ie.setu.todomore.ui.screens.taskedit.TaskEditScreen
import ie.setu.todomore.ui.screens.tasklist.TaskListScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(navController = navController, startDestination = "tasklist", modifier = modifier){
        composable("taskList"){
            TaskListScreen()
        }
    }
}


/*
@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = TaskList.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = TaskCreate.route) {
            // Call for the 'Task Creation' Screen
            TaskCreateScreen(modifier = modifier)
        }

        composable(route = TaskList.route) {
            // Call for the 'Task List' Screen
            TaskListScreen(modifier = modifier,
                onClickTaskDetails = {
                    taskId : Int ->
                    navController.navigateToTaskEdit(taskId)
                },
            )
        }

        composable(route = About.route) {
            // Call for the 'About' Screen
            AboutScreen(modifier = modifier)
        }

        composable(
            route = TaskEdit.route,
            arguments = TaskEdit.arguments
        )
        { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(TaskEdit.idArg)
            if (id != null) {
                TaskEditScreen()
            }
        }
    }
}

private fun NavHostController.navigateToTaskEdit(taskId: String) {
    this.navigate("taskedit/$taskId")
}*/
