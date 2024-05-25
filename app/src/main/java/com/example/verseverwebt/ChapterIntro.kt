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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class ChapterIntro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        turnOffFlashlight()

        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ChapterIntroContent()
                }
            }
        }
    }
    private fun turnOffFlashlight() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraManager.setTorchMode(cameraManager.cameraIdList[0], false)
    }
}

@Composable
fun ChapterIntroContent() {
    var flashlightOn by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    DisposableEffect(Unit) {
        val torchCallback = object : CameraManager.TorchCallback() {
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                flashlightOn = enabled
            }
        }

        cameraManager.registerTorchCallback(torchCallback, null)
        onDispose {
            cameraManager.unregisterTorchCallback(torchCallback)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (flashlightOn) Color.Transparent else Color.Black),
        contentAlignment = Alignment.Center
    ) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "CHAPTER",
                fontFamily = playfair,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                color = if (flashlightOn) Color.Black else Color.Gray
            )
            Text(
                text = "Intro",
                fontFamily = inspiration,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,

                color = if (flashlightOn) Color.Black else Color.Gray
            )
            Text(
                text = if (flashlightOn) "Das Abenteuer beginnt, das erste Rätsel gelöst, Doch im Dunkeln liegen noch viele verschlüsselt. Setze dein Lesezeichen, verirre dich nicht." else "Bringe Licht ins Dunkle....",
                fontFamily = playfair,
                style = MaterialTheme.typography.bodySmall,
                fontSize = if (flashlightOn) 13.sp else 20.sp,
                textAlign = if (flashlightOn) TextAlign.Left else TextAlign.Center,
                modifier = Modifier.padding(all = 50.dp),
                color = if (flashlightOn) Color.Black else Color.Gray
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ChapterIntroContentPreview() {
    VerseVerwebtTheme {
        ChapterIntroContent()
    }
}