package com.example.verseverwebt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun ButtonColumn(primaryText: String, fontSize: TextUnit, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = primaryText,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = playfair,
                fontSize = fontSize
            )
        }
    }
}
