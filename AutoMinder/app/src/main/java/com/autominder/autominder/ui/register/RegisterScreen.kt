package com.autominder.autominder.ui.register

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.ui.login.ui.Login
import com.autominder.autominder.ui.login.ui.LoginUiStatus
import com.autominder.autominder.ui.login.ui.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
@Preview(showBackground = true)
@Composable
fun RegisterScreen() {
    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory)
    val navController: NavHostController
    Register(viewModel, navController)

}

@Composable
fun Register(viewModel: RegisterViewModel, navController: NavHostController) {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RegisterForm(
            modifier =
            Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            viewModel = viewModel,
            navController = navController
        )
    }

}

*/


@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory),
    application: AutoMinderApplication = AutoMinderApplication()
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RegisterForm(
            Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            viewModel,
            navController
        )
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier,
    viewModel: RegisterViewModel,
    navController: NavHostController
) {
    val name: String by viewModel.name.collectAsState("")
    val email: String by viewModel.email.collectAsState("")
    val password: String by viewModel.password.collectAsState("")
    val passwordConfirm: String by viewModel.passwordConfirm.collectAsState("")
    val isLoading: Boolean by viewModel.isLoading.collectAsState(false)
    val registerEnable: Boolean by viewModel.registerEnabled.collectAsState(false)
    val coroutineScope = rememberCoroutineScope()
    val status by viewModel.status.observeAsState(initial = RegisterUiStatus.Resume)
    val application: AutoMinderApplication = LocalContext.current.applicationContext as AutoMinderApplication

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {

        val lifecycle = LocalLifecycleOwner.current
        LaunchedEffect(lifecycle) {
            viewModel.status.observe(lifecycle) { status ->
                when (status) {
                    is RegisterUiStatus.Success -> {
                        viewModel.clearStatus()
                        viewModel.clearData()
                        navController.navigate("login")
                    }

                    is RegisterUiStatus.ErrorWithMessage -> {

                    }

                    is RegisterUiStatus.Error -> {

                    }

                    else -> {}
                }

            }
        }



        Column(modifier) {
            HeaderTitle()
            RegisterBox(
                name = name,
                email = email,
                password = password,
                confirmPassword = passwordConfirm,
                registerEnable = registerEnable,
                viewModel = viewModel,
                coroutineScope = coroutineScope,
                navController = navController,
                status = status

            )
        }


    }
}


@Composable
fun HeaderTitle() {
    Text(
        text = "Registro de autominder", fontSize = 24.sp, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RegisterBox(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    registerEnable: Boolean,
    viewModel: RegisterViewModel,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    status: RegisterUiStatus
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(450.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            NameTextField(name) { viewModel.onRegisterChange(it, email, password, confirmPassword) }
            EmailTextField(email) {
                viewModel.onRegisterChange(
                    name,
                    it,
                    password,
                    confirmPassword
                )
            }
            PasswordTextField(password) {
                viewModel.onRegisterChange(
                    name,
                    email,
                    it,
                    confirmPassword
                )
            }
            ConfirmPasswordTextField(confirmPassword) {
                viewModel.onRegisterChange(
                    name,
                    email,
                    password,
                    it
                )
            }
            RegisterButton(registerEnable) {
                coroutineScope.launch {
                    viewModel.onRegisterChange(
                        name,
                        email,
                        password,
                        confirmPassword
                    )
                    viewModel.register(name, email, password)
                }
            }
        }
    }
}

@Composable
fun NameTextField(
    name: String,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onTextFieldChanged,
        label = { Text(text = "Nombre", color = MaterialTheme.colorScheme.onPrimaryContainer) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        placeholder = { Text(text = "Nombre") }
    )
}

@Composable
fun EmailTextField(
    email: String,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onTextFieldChanged,
        label = {
            Text(
                text = "Correo electrónico",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        placeholder = { Text(text = "example@example.com") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onTextFieldChanged,
        label = { Text(text = "Contraseña", color = MaterialTheme.colorScheme.onPrimaryContainer) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
fun ConfirmPasswordTextField(
    Confirmpassword: String,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = Confirmpassword,
        onValueChange = onTextFieldChanged,
        label = {
            Text(
                text = "Confirmar contraseña",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
fun RegisterButton(
    registerEnable: Boolean,
    onRegisterSelected: () -> Unit
) {
    Button(
        onClick = { onRegisterSelected() },
        enabled = registerEnable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "Registrarse", color = MaterialTheme.colorScheme.onPrimary)
    }
}