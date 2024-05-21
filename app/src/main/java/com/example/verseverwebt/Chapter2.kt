package com.example.verseverwebt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.verseverwebt.theme.inspiration
import com.example.verseverwebt.theme.playfair
import com.example.verseverwebt.ui.pages.VerseVerwebtTheme

class Chapter2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Chapter2Content()
                }
            }
        }
    }
}

@Composable
fun Chapter2Content() {


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp), // Hier wird ein Abstand von 8dp zwischen den Elementen eingefügt
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackToMenuButton()

        Spacer(modifier = Modifier.height(32.dp)) // Hier kannst du die Höhe des Spacers anpassen, um die Lücke zu reduzieren

        Text(
            text = "CHAPTER",
            fontFamily = playfair,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            textAlign = TextAlign.Center

        )

        Text(
            text = "Two",
            fontFamily = inspiration,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Im Norden steht eine Statue starr und kalt, ihr Blick richtet sich nach Osten, doch niemals in den Süden, denn ihr  Herz wird sich immer nach dem Westen sehnen.",
            fontFamily = playfair,
            fontSize = 13.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(all = 50.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.compas), // Verweis auf dein benutzerdefiniertes Icon
            contentDescription = "Kompass",
            tint = Color.Unspecified, // Dies verhindert, dass das Icon eingefärbt wird
            modifier = Modifier.size(260.dp)
        )

    }
}



@Preview(showBackground = true)
@Composable
fun Chapter2ContentPreview() {
    VerseVerwebtTheme {
        Chapter2Content()
    }
}