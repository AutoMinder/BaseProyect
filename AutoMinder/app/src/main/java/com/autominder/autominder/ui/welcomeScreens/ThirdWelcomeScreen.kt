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
import androidx.compose.ui.graphics.Color
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
fun ThirdScreen(
    navController: NavHostController,
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(content = {
            item {
                Third(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    navController = navController
                )
            }
        })
    }
}




@Composable
fun Third(modifier: Modifier, navController: NavHostController) {

    Column(modifier) {

        HeaderTitleThird()
        Spacer(modifier = Modifier.padding(8.dp))
        ImageContainerThird()
        Spacer(modifier = Modifier.padding(16.dp))
        DescriptionTextThird()
        FooterTextThird()
        Spacer(modifier = Modifier.padding(24.dp))
        NextScreenThird(navController)
        }
}



@Preview
@Composable
fun HeaderTitleThird() {
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
fun ImageContainerThird(

){
    Image(
        painter = painterResource(id = R.drawable.img_3),
        contentDescription = "Welcome Image",
        modifier = Modifier
            .height(300.dp)
            .width(350.dp)
            .clip(RoundedCornerShape(16.dp))


    )
}

@Preview
@Composable
fun DescriptionTextThird() {
    Text(
        text = "Verificación en tiempo real", fontSize = 18.sp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}


@Preview
@Composable
fun FooterTextThird() {
    Text(
        text = "Toda la información que necesitas a la brevedad", fontSize = 14.sp,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}


@Composable
fun NextScreenThird(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("login") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 0.dp, start = 25.dp, end = 25.dp)
    ) {
        Text(text = "Comencemos"
            , fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, bottom = 0.dp, start = 25.dp, end = 25.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold)
    }
}