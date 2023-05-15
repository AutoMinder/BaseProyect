package com.autominder.autominder.userInfo.changePassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.material3.CardDefaults

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    val viewModel = ChangePasswordViewModel()
    Box(Modifier.fillMaxSize()) {
        ChangePasswordScreen(viewModel)
    }
}

@Composable
fun ChangePasswordScreen(viewModel: ChangePasswordViewModel) {
    val actualPassword: String by viewModel.actualPassword.observeAsState(initial = "")
    val newPassword: String by viewModel.newPassword.observeAsState(initial = "")
    val confirmNewPassword: String by viewModel.confirmNewPassword.observeAsState(initial = "")
    val changePasswordEnable: Boolean by viewModel.changePasswordEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()
    val toastMessage: Boolean by viewModel.toastMessage.observeAsState(initial = false)

    if (toastMessage && !isLoading) {
        PasswordChangedMessage()
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderTitle()
            Spacer(modifier = Modifier.padding(35.dp))
            ChangePasswordBox(
                viewModel,
                actualPassword,
                newPassword,
                confirmNewPassword,
                changePasswordEnable,
                coroutineScope
            )
        }
    }
}

@Composable
fun HeaderTitle() {
    Text(
        text = "Autominder security", fontSize = 24.sp, modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ChangePasswordBox(
    viewModel: ChangePasswordViewModel,
    actualPassword: String,
    newPassword: String,
    confirmNewPassword: String,
    changePasswordEnable: Boolean,
    coroutineScope: CoroutineScope
) {
    Card(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
            .height(350.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ChangePasswordHeader()
            ChangePasswordFieldsWrapper(viewModel, actualPassword, newPassword, confirmNewPassword)
            ChangePasswordButton(changePasswordEnable) {
                coroutineScope.launch {
                    viewModel.onPasswordSelected()
                }
            }
        }
    }

}

@Composable
fun ChangePasswordHeader() {
    Text(
        text = "Cambiar contraseña",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun ChangePasswordFieldsWrapper(
    viewModel: ChangePasswordViewModel,
    actualPassword: String,
    newPassword: String,
    confirmNewPassword: String
) {


    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ActualPasswordTextField(actualPassword) {
            viewModel.onPasswordChange(
                it,
                newPassword,
                confirmNewPassword
            )
        }
        NewPasswordTextField(newPassword) {
            viewModel.onPasswordChange(
                actualPassword,
                it,
                confirmNewPassword
            )
        }
        ConfirmNewPasswordTextField(confirmNewPassword) {
            viewModel.onPasswordChange(
                actualPassword,
                newPassword,
                it
            )
        }
    }
}


@Composable
fun ActualPasswordTextField(actualPassword: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = actualPassword,
        onValueChange = { onPasswordChange(it) },
        label = { Text("Contraseña actual") },
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
fun NewPasswordTextField(newPassword: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = newPassword,
        onValueChange = { onPasswordChange(it) },
        label = { Text("Nueva contraseña") },
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
fun ConfirmNewPasswordTextField(confirmNewPassword: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = confirmNewPassword,
        onValueChange = { onPasswordChange(it) },
        label = { Text("Confirmar nueva contraseña") },
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
fun ChangePasswordButton(changePasswordEnable: Boolean, onPasswordSelected: () -> Unit) {

    Button(
        onClick = { onPasswordSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp),
        enabled = changePasswordEnable
    ) {
        Text(text = "Cambiar contraseña", color = Color.White)
    }
}

/*
    TODO(): Hacer un toast mas bonito
 */
@Composable
fun PasswordChangedMessage() {
    Toast.makeText(
        LocalContext.current,
        "La contraseña ha sido cambiada con exito",
        Toast.LENGTH_SHORT
    ).show()
}