package ie.setu.todomore.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ie.setu.todomore.ui.screens.about.AboutScreen
import ie.setu.todomore.ui.screens.taskcreate.TaskCreateScreen
import ie.setu.todomore.ui.screens.taskedit.TaskEditScreen
import ie.setu.todomore.ui.screens.tasklist.TaskListScreen
import ie.setu.todomore.ui.screens.login.LoginScreen
import ie.setu.todomore.ui.screens.register.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: String, modifier: Modifier = Modifier){
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier){
        composable("taskList"){
            TaskListScreen(navController = navController)
        }
        composable("taskCreate"){
            TaskCreateScreen(navController = navController)
        }
        composable("taskEdit/{taskId}", arguments = listOf(navArgument("taskId"){type = NavType.StringType})
        ){ backStackEntry ->
            TaskEditScreen(navController = navController)
            /*val taskIdString = backStackEntry.arguments?.getString("taskId") ?: "-1"
            val taskId = taskIdString.toLongOrNull() ?: -1L
            TaskEditScreen(navController = navController,taskId = taskId)*/
        }
        composable("about"){
            AboutScreen(navController = navController)
        }
        composable(Login.route){
            LoginScreen(navController = navController)
        }
        composable(Register.route){
            RegisterScreen(navController = navController)
        }
    }
}

private fun NavHostController.navigateToTaskEdit(taskId: String) {
    this.navigate("taskEdit/$taskId")
}

