package com.example.verseverwebt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class Chapter1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Chapter1Content()
                }
            }
        }
    }
}

@Composable
fun Chapter1Content() {
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
            text = "One",
            fontFamily = inspiration,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 66.dp)
        )
        Text(
            text = "MAN HÖRT GEFLÜSTER",
            fontFamily = playfair,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 26.dp)
        )
        Text(
            text = "Im Rätselmeer verborgen, liegt ein funkelnder Schatz, Klugheit und Weisheit sind der Schlüssel zum Glanz.",
            fontFamily = playfair,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 5.sp,
            textAlign = TextAlign.Center,

            )
    }
}



@Preview(showBackground = true)
@Composable
fun Chapter1ContentPreview() {
    VerseVerwebtTheme {
        Chapter1Content()
    }
}
