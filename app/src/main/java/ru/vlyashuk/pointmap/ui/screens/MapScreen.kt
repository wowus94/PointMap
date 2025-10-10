package ru.vlyashuk.pointmap.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier
) {
    Text("Map Screen", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
}