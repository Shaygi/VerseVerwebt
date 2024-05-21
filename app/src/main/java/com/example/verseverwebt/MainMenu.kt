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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.theme.CustomTypography
import com.example.verseverwebt.theme.inspiration
import com.example.verseverwebt.theme.playfair
import com.example.verseverwebt.ui.pages.VerseVerwebtTheme

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
            text = "Verse verwebt",
            style = CustomTypography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Ein poetisches Abenteuer",
            style = CustomTypography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ButtonColumn("-Start-") {
            context.startActivity(Intent(context, Chapter1::class.java))
        }
        ButtonColumn("Inhaltsverzeichnis") {
            context.startActivity(Intent(context, TableOfContents::class.java))
        }
        ButtonColumn("Leaderboard") {
            // Add navigation for SettingsActivity
        }
        ButtonColumn("Credits") {
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
