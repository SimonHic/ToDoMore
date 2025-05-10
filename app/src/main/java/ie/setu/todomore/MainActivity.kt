package ie.setu.todomore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.todomore.navigation.AppNavGraph
import ie.setu.todomore.ui.components.general.BottomNavBar
import ie.setu.todomore.ui.theme.ToDoMoreTheme
import com.google.firebase.auth.FirebaseAuth
import ie.setu.todomore.navigation.Login
import ie.setu.todomore.navigation.TaskList
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.currentBackStackEntryAsState
import ie.setu.todomore.navigation.Register

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ToDoMoreTheme {
                ToDoMoreApp()
                }
            }
        }
    }

@Composable
fun ToDoMoreApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    // Access the current route dynamically
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route

    // Only run once per composition
    LaunchedEffect(key1 = true) {
        FirebaseAuth.getInstance().signOut()
    }
    val startDestination = Login.route

    Scaffold(
        modifier = modifier,
        //If the current routes are either Login or Register hide the bottom nav bar else show it
        bottomBar = {
            if(currentDestination != Login.route && currentDestination != Register.route){
                BottomNavBar(navController)
            }

        },
        content = { paddingValues ->
            AppNavGraph(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ToDoMoreAppPreview() {
    ToDoMoreTheme {
        ToDoMoreApp()
    }
}