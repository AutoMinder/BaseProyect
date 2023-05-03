package com.autominder.autominder.principalMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun PrincipalMenuScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PrincipalMenu()
    }
}

@Composable
fun PrincipalMenu(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
    ) {
        AutominderHeader()
    }
}

@Composable
fun AutominderHeader() {
    Text(
        text = "Autominder",
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )
}