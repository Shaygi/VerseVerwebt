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
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class Chapter1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the volume to 0 at the beginning
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)

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
    val context = LocalContext.current
    var textSize by remember { mutableStateOf(5.sp) }


    DisposableEffect(Unit) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                textSize = (5 + volume * 2).sp // Example calculation
            }
        }
        context.contentResolver.registerContentObserver(
            android.provider.Settings.System.CONTENT_URI,
            true,
            contentObserver
        )
        onDispose {
            context.contentResolver.unregisterContentObserver(contentObserver)
        }
    }

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
            fontSize = textSize,
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
