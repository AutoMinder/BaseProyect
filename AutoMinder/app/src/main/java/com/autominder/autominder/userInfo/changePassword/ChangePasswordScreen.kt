package com.autominder.autominder.userInfo.changePassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

//TODO: VIEWMODEL

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    val navController = rememberNavController()
    val viewModel = ChangePasswordViewModel()
    ChangePasswordScreen(navController, viewModel)
}

@Composable
fun ChangePasswordScreen(navController: NavController, viewModel: ChangePasswordViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderTitle()
        Spacer(modifier = Modifier.padding(40.dp))
        ChangePasswordBox()
    }
}


@Composable
fun HeaderTitle() {
    Text(
        text = "Autominder security", fontSize = 24.sp, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ChangePasswordBox() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(350.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ChangePasswordHeader()
            ChangePasswordFieldsWrapper()
            ChangePasswordButton()
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
        fontSize = 22.sp
    )
}

@Composable
fun ChangePasswordFieldsWrapper() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ActualPasswordTextField()
        NewPasswordTextField()
        ConfirmNewPasswordTextField()
    }
}


@Composable
fun ActualPasswordTextField() {
    OutlinedTextField(
        value = "",
        onValueChange = { /*TODO*/ },
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
fun NewPasswordTextField() {
    OutlinedTextField(
        value = "",
        onValueChange = { /*TODO*/ },
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
fun ConfirmNewPasswordTextField() {
    OutlinedTextField(
        value = "",
        onValueChange = { /*TODO*/ },
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
fun ChangePasswordButton() {

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp)
    ) {
        Text(text = "Cambiar contraseña", color = Color.White)
    }
}