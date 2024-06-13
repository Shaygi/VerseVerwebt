package com.example.verseverwebt

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

val inspiration = FontFamily(
    Font(R.font.inspiration_regular)
)
val playfair = FontFamily(
    Font(R.font.playfair_display)
)

//Main menu for navigation through menu elements
class MainMenu : ComponentActivity() {
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
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Woven verses",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            fontFamily = playfair,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "A poetic adventure",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 30.sp,
            fontFamily = inspiration,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        //Navigation buttons
        //Start button leads to the last accesable chapter
        ButtonColumn("-Start-", 18.sp) {
            context.startActivity(Intent(context, Chapter1::class.java))
        }
        //Table of contents button leads to the chapter navigation
        ButtonColumn("Table of Contents", 18.sp) {
            context.startActivity(Intent(context, TableOfContents::class.java))
        }
        //Leaderboard for viewing the ranklist
        ButtonColumn("Leaderboard", 18.sp) {
            // Add navigation for LeaderboardActivity
        }
        ButtonColumn("Credits", 18.sp) {
            // Add navigation for CreditsActivity
        }
    }
}

//function is for previewing in the IDE
@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    VerseVerwebtTheme {
        Content()
    }
}
