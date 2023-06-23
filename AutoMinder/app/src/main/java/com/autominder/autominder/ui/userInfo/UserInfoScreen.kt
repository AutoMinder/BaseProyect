package com.autominder.autominder.ui.userInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun UserInfoScreenPreview() {
    val viewModel: UserInfoViewModel = viewModel(factory = UserInfoViewModel.Factory)
    val navController = rememberNavController()
    UserInfoScreen(navController, viewModel)
}

@Composable
fun UserInfoScreen(navController: NavController, viewModel: UserInfoViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        UserInfo(
            navController,
            viewModel
        )
    }
}

@Composable
fun UserInfo(navController: NavController, viewModel: UserInfoViewModel) {
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            TitleBar("Usuario") // TODO(): Dato quemado cambiar cuando se tengan los users
            Spacer(modifier = Modifier.padding(15.dp))
            UserImage()
            Spacer(modifier = Modifier.padding(30.dp))
            ButtonWrapper(navController, coroutineScope, viewModel)
        }
    }

}

@Composable
fun TitleBar(name: String) {
    Text(
        text = "Hola, $name",
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun UserImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_user),
        contentDescription = "Icono de usuario",
        modifier = Modifier
            .padding(16.dp)
            .size(180.dp)
            .background(colorResource(id = R.color.icon_background), shape = CircleShape)
            .padding(8.dp),
    )

}


@Composable
fun ButtonWrapper(navController: NavController, coroutineScope: CoroutineScope, viewModel: UserInfoViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 0.dp)

        //ChangePasswordButton(navController, modifier)

        ContactDevelopersButton(modifier) { viewModel.onContactDevelopersClicked(context) }

        //TODO: AGREGAR PERMISO PARA ABRIR LINKS EXTERNOS (Biblioteca Accompanist)
        ObdBuyLinkButton(modifier) { viewModel.onBuyLinkClicked(context) }
        Logout(modifier) {
            coroutineScope.launch {
                viewModel.onLogoutClicked()
                navController.navigate("login")
            }
        }
    }
}

@Composable
fun ChangePasswordButton(navController: NavController, modifier: Modifier) {
    Button(
        //TODO(): Cambiar ruta de navegacion
        onClick = { navController.navigate("change_password") }, modifier
    ) {
        Text(text = "CAMBIAR CONTRASEÑA", color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun ContactDevelopersButton(
    modifier: Modifier,
    onContactDevelopersClicked: () -> Unit
) {
    Button(
        onClick = { onContactDevelopersClicked() }, modifier
    ) {
        Text(text = "CONTACTA A LOS DESARROLLADORES", color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun ObdBuyLinkButton(modifier: Modifier, onBuyLinkClicked: () -> Unit) {
    Button(
        onClick = { onBuyLinkClicked() }, modifier
    ) {
        Text(text = "LINK DE COMPRA DE OBD", color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun Logout(modifier: Modifier, onLogoutClicked: () -> Unit) {
    Button(
        onClick = { onLogoutClicked() }, modifier
    ) {
        Text(text = "CERRAR SESION", color = MaterialTheme.colorScheme.onPrimary)
    }
}