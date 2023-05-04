package com.autominder.autominder.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun TopBar() {
    androidx.compose.material.TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,

        ) {
        Text(
            text = "AutoMinder",
            modifier = Modifier.padding(18.dp, 0.dp, 0.dp, 0.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }

}