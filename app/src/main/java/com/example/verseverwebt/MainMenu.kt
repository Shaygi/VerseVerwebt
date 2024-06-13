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
import androidx.recyclerview.widget.RecyclerView
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

val inspiration = FontFamily(
    Font(R.font.inspiration_regular)
)
val playfair = FontFamily(
    Font(R.font.playfair_display)
)

class MainMenu : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
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
            ButtonColumn("-Start-", 18.sp) {
                context.startActivity(Intent(context, Chapter1::class.java))
            }
            ButtonColumn("Inhaltsverzeichnis", 18.sp) {
                context.startActivity(Intent(context, TableOfContents::class.java))
            }
            ButtonColumn("Profile", 18.sp) {
                // TODO: Add navigation for Profile or login
                context.startActivity(Intent(context, Login::class.java))
            }
            ButtonColumn("Leaderboard", 18.sp) {
                context.startActivity(Intent(context, Ranking::class.java))
            }
            ButtonColumn("Credits", 18.sp) {
            // Add navigation for CreditsActivity
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
}