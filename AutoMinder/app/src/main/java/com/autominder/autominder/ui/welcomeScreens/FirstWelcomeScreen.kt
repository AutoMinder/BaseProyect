package com.autominder.autominder.ui.welcomeScreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
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
import com.autominder.autominder.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun FirstScreen(
    navController: NavHostController,
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(content = {
            item {
                First(modifier = Modifier.fillMaxSize(), navController = navController)
            }
        })
    }
}



@Composable
fun First(modifier: Modifier, navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val application: AutoMinderApplication = LocalContext.current.applicationContext as AutoMinderApplication

    Column(modifier) {

        HeaderTitle()
        Spacer(modifier = Modifier.padding(4.dp))
        ImageContainer()
        Spacer(modifier = Modifier.padding(4.dp))
        DescriptionText()
        FooterText()
        Spacer(modifier = Modifier.padding(24.dp))
        NextScreen(navController)



        }
}


@Preview
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


@Preview
@Composable
fun ImageContainer(

){
    Image(

        painter = painterResource(id = R.drawable.img_1),
        contentDescription = "Welcome Image",

        modifier = Modifier
            .height(370.dp)
            .width(400.dp)
            .clip(RoundedCornerShape(16.dp))

    )
}

@Preview
@Composable
fun DescriptionText() {
    Text(
        text = "El motor es el corazón de tu vehiculo", fontSize = 18.sp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}


@Preview
@Composable
fun FooterText() {
    Text(
        text = "Nosotros te ayudamos, todo desde la palma de tu mano", fontSize = 14.sp,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}


@Composable
fun NextScreen(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("welcome2") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 0.dp, start = 25.dp, end = 25.dp)
    ) {
        Text(text = "Siguiente"
            , fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()

                .padding(top = 0.dp, bottom = 0.dp, start = 25.dp, end = 25.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold)
    }
}