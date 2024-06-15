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
import com.example.verseverwebt.ui.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

//First Chapter as story introduction
//player needs to rise the volume to solve the riddle
//should be solved after a text size of 26 is reached
class Chapter1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Access to Audio manager
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // Sets the volume to 0 at the beginning
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)

        //content of the page
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

    // Uses DisposableEffect to free resources if the effect is not used anymore
    DisposableEffect(Unit) {

        //gets audio manager from the context
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        //creates content observer to monitor changes in system audio settings
        val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            //called when audio changes
            override fun onChange(selfChange: Boolean) {
                //gets current column level of music stream
                val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                //updates text size based on volume level
                textSize = (5 + volume * 2).sp
            }
        }
        //registers content observer to listen for changes
        context.contentResolver.registerContentObserver(
            android.provider.Settings.System.CONTENT_URI,
            true,
            contentObserver
        )
        //cleans content observer when effect is not used
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
        //Title
        Text(
            text = "CHAPTER",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )
        //Subtitle
        Text(
            text = "One",
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 66.dp)
        )
        //Riddle text
        Text(
            text = "You hear whispering:",
            style = CustomTypography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        //clue that changes size when volume rises
        Text(
            text = "In the sea of riddles hidden, lies a sparkling prize,\nWisdom and cleverness are the keys to the skies.\nUnlocking the brilliance with insight so grand,\nTo reveal the treasure within your hand.",
            fontFamily = playfair,
            style = MaterialTheme.typography.bodySmall,
            fontSize = textSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(all = 26.dp)
        )
    }
}
//function is for previewing in the IDE
@Preview(showBackground = true)
@Composable
fun Chapter1ContentPreview() {
    // Sets the theme for the preview
    VerseVerwebtTheme {
        // Calls the composable function to be previewed
        Chapter1Content()
    }
}


