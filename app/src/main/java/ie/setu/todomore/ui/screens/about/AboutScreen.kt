package ie.setu.todomore.ui.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.todomore.navigation.About
import ie.setu.todomore.ui.components.general.TopAppBarPreview
import ie.setu.todomore.ui.components.general.TopAppBarProvider

// Screen to give a brief explanation of the app
@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier){
    val navigateUp: () -> Unit = {navController.navigateUp()}
    Column(modifier = modifier.padding(16.dp)){
        TopAppBarProvider(
            navController = navController,
            currentScreen = About,
            canNavigateBack = true,
            email = FirebaseAuth.getInstance().currentUser?.email?:"",
            name = FirebaseAuth.getInstance().currentUser?.displayName?:"",
            navigateUp = navigateUp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "ToDo More is a task management app that allows users to add, edit, and organise tasks with priority levels. MORE COMING SOON!")
    }
}