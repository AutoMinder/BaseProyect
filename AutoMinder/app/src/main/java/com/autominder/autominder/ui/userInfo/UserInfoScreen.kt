package com.autominder.autominder.ui.userInfo

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.R
import com.autominder.autominder.ui.myCars.ui.CardCar
import com.autominder.autominder.ui.myCars.ui.OwnCarsUiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    LaunchedEffect(Unit){
        viewModel.fetchUserName()
    }
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
    val application = LocalContext.current.applicationContext as AutoMinderApplication
    val apiData: State<String> = viewModel.apiData.collectAsState("")
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

//    fun handleUiStatus(status: UserInfoUiStatus) { //This function will handle the status of the screen
//        when (status) {
//            is UserInfoUiStatus.Success -> {
//                name = status.name
//                Toast.makeText(context, "Info obtained succesfully!", Toast.LENGTH_SHORT).show()
//                Log.d("Success", status.name)
//            }
//
//            is UserInfoUiStatus.Error -> {
//                Toast.makeText(context, "Error en el fetcheo de nombre", Toast.LENGTH_SHORT).show()
//                Log.d("MyCarsViewModel", "Error: ${status.exception}")
//            }
//
//            is UserInfoUiStatus.ErrorWithMessage -> {
//                Toast.makeText(
//                    context,
//                    "Error con mensaje en el fetcheo de nombre",
//                    Toast.LENGTH_SHORT
//                ).show()
//                Log.d("MyCarsViewModel", "ErrorWithMessage: ${status.message}")
//            }
//
//            else -> {
//                Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    Log.d("UserInfoScreen", "UserInfoScreen: ${apiData.value}")

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {



        item {

//            LaunchedEffect(coroutineScope) { //This is a coroutine that will be launched when the screen is created
//
//                //Coroutine that will observe the status of the view model
//                coroutineScope.launch {
//                    viewModel.status.observe(lifecycleOwner) { status -> //This is the observer of the status
//                        handleUiStatus(status) //This function will handle the status //This will get the name from the status (if it is success
//                    }
//                }
//            }

            apiData.value.let { data ->
                TitleBar(data)
                Spacer(modifier = Modifier.padding(15.dp))
                UserImage()
                Spacer(modifier = Modifier.padding(30.dp))
                ButtonWrapper(navController, coroutineScope, application, viewModel)
            }
        }

//
//
//        if (viewModel.status.value is UserInfoUiStatus.Success) {
//            name = (viewModel.status.value as UserInfoUiStatus.Success).name
//
//        } else {
//            item {
//                TitleBar("Usuario")
//                Spacer(modifier = Modifier.padding(15.dp))
//                UserImage()
//                Spacer(modifier = Modifier.padding(30.dp))
//                ButtonWrapper(navController, coroutineScope, application, viewModel)
//            }
//        }
    }
}

fun <T> LiveData<T>.asStateFlow(): StateFlow<T?> {
    val stateFlow = MutableStateFlow(value)
    observeForever { stateFlow.value = it }
    return stateFlow
}

@Composable
fun TitleBar(name: String) {
    Text(
        text = "Hola, $name",
        fontSize = 32.sp,
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
            .padding(8.dp)
            .size(180.dp)
            .background(colorResource(id = R.color.icon_background), shape = CircleShape)
            .padding(8.dp),
    )

}


@Composable
fun ButtonWrapper(navController: NavController, coroutineScope: CoroutineScope, application: AutoMinderApplication, viewModel: UserInfoViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
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
                application.clearAuthToken()
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
        Text(
            text = "CONTACTA A LOS DESARROLLADORES",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
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