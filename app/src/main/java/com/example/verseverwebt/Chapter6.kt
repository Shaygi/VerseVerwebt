package com.example.verseverwebt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme
import kotlinx.coroutines.delay

class Chapter6 : ComponentActivity() {

    private lateinit var chargingReceiver: BroadcastReceiver
    private lateinit var achieved: MutableState<Boolean>
    private lateinit var isCharging: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        achieved = mutableStateOf(false)
        isCharging = mutableStateOf(false)

        chargingReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                isCharging.value = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
                if (isCharging.value) {
                    achieved.value = true
                }
            }
        }

        registerReceiver(chargingReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Chapter6Content(isCharging = isCharging.value, achieved = achieved.value)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(chargingReceiver)
    }
}

@Composable
fun Chapter6Content(isCharging: Boolean, achieved: Boolean) {
    var showInitialText by remember { mutableStateOf(true) }

    val initialMessage = "Eine lange Reise hast du hinter dich gebracht,\n viele Nächte warst du wach, \nes wird Zeit sich hinzulegen, \n und deine Energie zu regenerieren."
    val finalMessage = "Wieder bist du voller Kraft,\n die Reise noch lange nicht geschafft,\n nun musst du ein bisschen weiter gehen,\n um schließlich deinen Schatz zu sehen."

    val backgroundColor by animateColorAsState(
        targetValue = if (achieved) Color.White else Color.Gray,
        animationSpec = tween(durationMillis = 2000)
    )

    val textColor by animateColorAsState(
        targetValue = if (achieved) Color.Black else Color.DarkGray,
        animationSpec = tween(durationMillis = 2000)
    )

    LaunchedEffect(isCharging) {
        if (isCharging) {
            delay(2000) // Optional delay before changing the text
            showInitialText = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.spacedBy(16.dp),
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

        AnimatedTextFlashing(
            text = if (showInitialText) initialMessage else finalMessage,
            color = textColor
        )
    }
}

@Composable
fun AnimatedTextFlashing(text: String, color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Text(
        text = text,
        style = CustomTypography.bodyMedium.copy(fontSize = 20.sp),
        textAlign = TextAlign.Center,
        color = color.copy(alpha = alpha),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun Chapter6ContentPreview() {
    VerseVerwebtTheme {
        Chapter6Content(isCharging = false, achieved = false)
    }
}


