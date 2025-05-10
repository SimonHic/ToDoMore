package ie.setu.todomore.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.todomore.navigation.About
import ie.setu.todomore.navigation.Login
import ie.setu.todomore.ui.theme.ToDoMoreTheme
@Composable
fun DropDownMenu(navController: NavController) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Help") }

    Box(
        contentAlignment = Alignment.Center,

        ) {
        // 3 vertical dots icon
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Open Info",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(color = Color.White,text = "Info", fontSize = 18.sp) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Info",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                },
                onClick = {
                    selectedOptionText = "Info"
                    expanded = false
                    navController.navigate(About.route)
                },
            )
            DropdownMenuItem(
                text = { Text(color = Color.White,text = "Logout", fontSize = 18.sp) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                },
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Login.route){
                        popUpTo(0)
                    }
                    expanded = false
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {
    ToDoMoreTheme {
        DropDownMenu(navController = rememberNavController())
    }
}