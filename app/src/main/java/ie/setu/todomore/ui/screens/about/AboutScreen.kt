package ie.setu.todomore.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.todomore.navigation.About
import ie.setu.todomore.ui.components.general.TopAppBarPreview
import ie.setu.todomore.ui.components.general.TopAppBarProvider
import ie.setu.todomore.ui.theme.Purple80

// Screen to give a brief explanation of the app
@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier) {
    val navigateUp: () -> Unit = { navController.navigateUp() }

    Scaffold(
        topBar = {
            TopAppBarProvider(
                navController = navController,
                currentScreen = About,
                canNavigateBack = true,
                email = FirebaseAuth.getInstance().currentUser?.email ?: "",
                name = FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                navigateUp = navigateUp
            )
        }
    ) { paddingValues ->

        Column(modifier = modifier.padding(paddingValues).padding(16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth().background(Purple80, shape = MaterialTheme.shapes.medium).padding(16.dp)
            ){
                Column{
                    Text(text = "Long press on a task to mark it for completion. Then press the Clear Completed button to get rid of any completed tasks.")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Long press again to unmark it.")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Use the Focus Mode to show only High-Priority tasks.")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "ToDo More is a task management app that allows users to add, edit, and organise tasks with priority levels!")
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}