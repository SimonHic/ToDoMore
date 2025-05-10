package ie.setu.todomore.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ie.setu.todomore.R
import ie.setu.todomore.firebase.auth.Response
import ie.setu.todomore.navigation.TaskList
import ie.setu.todomore.navigation.Login
import ie.setu.todomore.navigation.Register
import ie.setu.todomore.ui.components.general.ButtonComponent
import ie.setu.todomore.ui.components.general.ClickableLoginTextComponent
import ie.setu.todomore.ui.components.general.GoogleSignInButtonComponent
import ie.setu.todomore.ui.components.general.HeadingLogoComponent
import ie.setu.todomore.ui.components.general.HeadingTextComponent
import ie.setu.todomore.ui.components.general.MyTextFieldComponent
import ie.setu.todomore.ui.components.general.PasswordTextFieldComponent
import ie.setu.todomore.ui.components.general.ShowLoader
import ie.setu.todomore.ui.components.general.UnderLinedTextComponent
import ie.setu.todomore.ui.theme.ToDoMoreTheme

@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()) {

    var isEnabled by remember { mutableStateOf(false) }
    val loginFlow = loginViewModel.loginFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // NormalTextComponent(value = stringResource(id = R.string.login))
                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(20.dp))
                HeadingLogoComponent()
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(labelValue = stringResource(id = R.string.email),
                    rememberVectorPainter(Icons.Default.Email),
                    //painterResource(id = android.R.drawable.ic_dialog_email),
                    //painterResource(id = R.drawable.message),
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    rememberVectorPainter(Icons.Default.Lock),
                    //painterResource(id = android.R.drawable.ic_lock_lock),
                    //painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(20.dp))
                UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))

                Spacer(modifier = Modifier.height(30.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                        onLogin()
                        //navController.navigate(Report.route)
                        //  { launchSingleTop = true }
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value

                )
                isEnabled = loginViewModel.allValidationsPassed.value

                // Google Button here
                Spacer(modifier = Modifier.height(10.dp))
                val context = LocalContext.current
                GoogleSignInButtonComponent {
                    loginViewModel.signInWithGoogleCredentials(context)
                }

                Spacer(modifier = Modifier.height(10.dp))
                ClickableLoginTextComponent (
                    tryingToLogin = false,
                    onTextSelected = {navController.navigate(Register.route)}
                    )
            }
        }
    }

    loginFlow.value?.let {
        when (it) {
            is Response.Failure -> {
                val context = LocalContext.current
                Toast.makeText(context, it.e.message, Toast.LENGTH_LONG).show()
                navController.popBackStack()
                navController.navigate(Login.route)
                //      ShowSnackBar(message = it.exception.message.toString())
            }
            is Response.Loading -> {
                //CircularProgressIndicator()
                ShowLoader(message = "Please Wait...")
            }
            is Response.Success -> {
                LaunchedEffect(Unit) {
                    //         navController.popBackStack()
                    navController.navigate(TaskList.route) {
                        popUpTo(Login.route) {
                            //       navController.popBackStack()
                            inclusive = true
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ToDoMoreTheme {
        PreviewLoginScreen()
    }
}

@Composable
fun PreviewLoginScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                //  NormalTextComponent(value = stringResource(id = R.string.login))
                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(10.dp))
                HeadingLogoComponent()
                Spacer(modifier = Modifier.height(10.dp))

                MyTextFieldComponent(labelValue = stringResource(id = R.string.email),
                    rememberVectorPainter(Icons.Default.Email),
                    //painterResource(id = android.R.drawable.ic_dialog_email),
                    //painterResource(id = R.drawable.message),
                    onTextChanged = {
                        //loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = true
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    rememberVectorPainter(Icons.Default.Lock),
                    //painterResource(id = android.R.drawable.ic_lock_lock),
                    //painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        //loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = true
                )

                Spacer(modifier = Modifier.height(10.dp))
                UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))

                Spacer(modifier = Modifier.height(10.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        //loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = false
                )
                Spacer(modifier = Modifier.height(10.dp))
                GoogleSignInButtonComponent {
                    //  loginViewModel.oneTapSignIn()
                }
            }
        }
    }
}