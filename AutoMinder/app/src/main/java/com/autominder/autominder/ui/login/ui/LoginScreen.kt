package com.autominder.autominder.ui.login.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.autominder.autominder.AutoMinderApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(
            Modifier
                .align(Alignment.Center)

                .fillMaxSize(),
            viewModel,
            navController
        )
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavHostController) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnabled.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val status by viewModel.status.observeAsState(initial = LoginUiStatus.Resume)
    val coroutineScope = rememberCoroutineScope()
    val application: AutoMinderApplication =
        LocalContext.current.applicationContext as AutoMinderApplication
    val context = LocalContext.current

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        val lifecycle = LocalLifecycleOwner.current
        LaunchedEffect(lifecycle) {
            viewModel.status.observe(lifecycle) { status ->
                when (status) {
                    is LoginUiStatus.Success -> {
                        val token = status.token
                        viewModel.updateToken(token)
                        application.saveAuthToken(token)
                        viewModel.saveUserData(token)
                        Log.d("LoginScreen", "token: ${application.getToken()}")

                        navController.navigate("principal_menu")
                    }

                    is LoginUiStatus.ErrorWithMessage -> {
                        Toast.makeText(
                            context,
                            status.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is LoginUiStatus.Error -> {
                        Toast.makeText(
                            context,
                            "Error al iniciar sesión",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }

            }
        }


        Column(modifier) {

            HeaderTitle()
            Spacer(modifier = Modifier.padding(40.dp))

            LoginBox(
                email,
                viewModel,
                password,
                loginEnable,
                coroutineScope,
                navController,
                status,
                application
            )

            Spacer(modifier = Modifier.padding(40.dp))
            RegisterBox(navController)
        }
    }


}

@Composable
fun ShowToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val context = LocalContext.current
    Toast.makeText(context, message, duration).show()
}

@Composable
fun HeaderTitle() {
    Text(
        text = "Bienvenido a Autominder", fontSize = 24.sp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun LoginBox(
    email: String,
    viewModel: LoginViewModel,
    password: String,
    loginEnable: Boolean,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    status: LoginUiStatus,
    application: AutoMinderApplication


) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
        ),
        modifier = Modifier
//            .background(color = Color.White)
            .height(350.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {

            AccountHeader()
            EmailTextField(email) { viewModel.onLoginChange(it, password) }
            Spacer(modifier = Modifier.padding(8.dp))

            PasswordTextField(password) { viewModel.onLoginChange(email, it) }

            Spacer(modifier = Modifier.padding(8.dp))

            LoginButton(loginEnable) {
                coroutineScope.launch {
                    Log.d("LoginScreen", "email: $email, password: $password")
                    delay(1000)
                    viewModel.login(email, password)
                }
            }
        }
    }
}

@Preview
@Composable
fun AccountHeader() {
    Text(
        text = "Ingresa a tu cuenta",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold

        )
}

@Composable
fun ForgotPassword(navController: NavHostController) {
    Text(
        text = "¿Olvidaste tu contraseña?",
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("forgot_password") }
            .padding(8.dp),
        textAlign = TextAlign.End,

        ) //TODO: Add clickable
}

@Composable
fun EmailTextField(email: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Email") },
        shape = MaterialTheme.shapes.small,

        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),

        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = { Text("example@example.com") },


        )
}

@Composable
fun PasswordTextField(password: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Password") },
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        enabled = loginEnable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(text = "Iniciar sesión")
    }

}

@Composable
fun RegisterBox(navController: NavHostController) {
    Text(
        text = "¿No tienes cuenta?", modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), textAlign = TextAlign.Center
    )
    RegisterButton(navController)
}

@Composable
fun RegisterButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("register") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 0.dp, start = 25.dp, end = 25.dp)
    ) {
        Text(text = "Regístrate")
    }
}