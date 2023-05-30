package com.autominder.autominder.obdSensor.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController


@SuppressLint("MissingPermission")
@Composable
fun ObdSensorConnectScreen(
    obdSensorViewModel: ObdSensorViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val bluetoothEnabled by obdSensorViewModel.bluetoothEnabled.collectAsState(false)
    val discoveredDevices by obdSensorViewModel.discoveredDevices.collectAsState(emptyList())


    LaunchedEffect(Unit) {
        val permissionGranted = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            obdSensorViewModel.verifyBluetoothEnabled(context)
            obdSensorViewModel.startBluetoothDiscovery(context)
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                200
            )
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)

    ) {
        items(discoveredDevices.size) {
            val device = discoveredDevices.getOrNull(it)
            if (!bluetoothEnabled) {
                Text(text = "Bluetooth is not enabled")
            } else {

                Text(text = device?.name ?: "Unknown Device")
            }

        }
    }
}