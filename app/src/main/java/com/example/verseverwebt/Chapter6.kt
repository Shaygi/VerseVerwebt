package com.example.verseverwebt

import android.content.Context
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.verseverwebt.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class Chapter6 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Chapter6Content()
                }
            }
        }
    }
}

@Composable
fun Chapter6Content() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackToMenuButton()

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "CHAPTER",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Six",
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 66.dp)
        )
        Text(
            text = "Eine lange Reise hast du hinter dich gebracht,\n viele Nächte warst du wach, \nes wird Zeit sich hinzulegen, \n und deine Energie zu regenerieren.",
            style = CustomTypography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Chapter6ContentPreview() {
    VerseVerwebtTheme {
        Chapter6Content()
    }
}

