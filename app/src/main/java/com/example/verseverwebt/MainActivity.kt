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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

val inspiration = FontFamily(
    Font(R.font.inspiration_regular)
)
val playfair = FontFamily(
    Font(R.font.playfair_display)
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Verse verwebt",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            fontFamily = playfair,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Ein poetisches Abenteuer",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 30.sp,
            fontFamily = inspiration,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ButtonColumn("-Start-",  18.sp)
        ButtonColumn("Inhaltsverzeichnis",  18.sp)
        ButtonColumn("Einstellungen",  18.sp)
        ButtonColumn("Credits",  18.sp)
    }
}

@Composable
fun ButtonColumn(primaryText: String, fontSize: TextUnit) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { /* Handle button click */ },
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

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    VerseVerwebtTheme {
        Content()
    }
}
