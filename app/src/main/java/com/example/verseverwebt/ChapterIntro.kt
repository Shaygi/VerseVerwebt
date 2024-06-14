package com.example.verseverwebt

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class ChapterIntro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Turn flashlight off on create
        turnOffFlashlight()
        // Content of the page
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ChapterIntroContent()
                }
            }
        }
    }

    // Function for turning off the flashlight
    private fun turnOffFlashlight() {
        // Access to Camera manager
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        // Sets flashlight mode to false
        cameraManager.setTorchMode(cameraManager.cameraIdList[0], false)
    }
}

@Composable
fun ChapterIntroContent() {
    // Saves status of the flashlight
    var flashlightOn by remember { mutableStateOf(false) }
    // Current context
    val context = LocalContext.current
    // Camera service from current context
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    // Uses DisposableEffect to free resources if the effect is not used anymore
    DisposableEffect(Unit) {
        // Creates a TorchCallback instance to react to changes from the flashlight mode
        val torchCallback = object : CameraManager.TorchCallback() {
            // Is called if flashlight mode changes
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                flashlightOn = enabled // Updates the status of the flashlight
            }
        }
        // Registers TorchCallback at CameraManager
        cameraManager.registerTorchCallback(torchCallback, null)

        // Frees resources if the effect is not used anymore
        onDispose {
            // Removes the TorchCallback from CameraManager
            cameraManager.unregisterTorchCallback(torchCallback)
        }
    }

    val initialText = "Das Abenteuer beginnt, das erste Rätsel gelöst, Doch im Dunkeln liegen noch viele verschlüsselt. Setze dein Lesezeichen, verirre dich nicht."
    val placeholderText = "Bringe Licht ins Dunkle...."

    // Manages the currently visible portion of the text


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (flashlightOn) Color.Transparent else Color.Black), // Background color changes depending on the flashlight status
        contentAlignment = Alignment.Center
    ) {
        // Menu Button appears when flashlight is on
        if (flashlightOn) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                BackToMenuButton()
            }
        }
        Column(
            // Column alignment
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                // Color changes when flashlight is on
                text = "CHAPTER",
                fontFamily = playfair,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                color = if (flashlightOn) Color.Black else Color.Gray
            )
            // Subtitle
            Text(
                // Color changes when flashlight is on
                text = "Intro",
                fontFamily = inspiration,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                color = if (flashlightOn) Color.Black else Color.Gray
            )
            if(flashlightOn){
                // Success text with typewriter effect
                AnimatedTypewriterText(text = initialText, fontSize = 13, textAlign = TextAlign.Left, color = Color.Black)

            }else{
                // Riddle text with Fade in effect
                AnimatedFadeInText(
                    text = placeholderText,
                    fontSize = 20,
                    textAlign = TextAlign.Left,
                    color = Color.Gray
                )
            }
        }
    }
}

// Function is for previewing in the IDE
@Preview(showBackground = true)
@Composable
fun ChapterIntroContentPreview() {
    // Sets the theme for the preview
    VerseVerwebtTheme {
        // Calls the composable function to be previewed
        ChapterIntroContent()
    }
}



