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
import com.example.verseverwebt.ui.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme
import kotlinx.coroutines.delay

//Sixth Chapter
//Player needs to charge phone
class Chapter6 : ComponentActivity() {

    private lateinit var chargingReceiver: BroadcastReceiver
    private lateinit var achieved: MutableState<Boolean>
    private lateinit var isCharging: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //if battery is already loading the state is set false
        achieved = mutableStateOf(false)
        isCharging = mutableStateOf(false)

        //listener to react to battery status changes
        chargingReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                isCharging.value = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
                if (isCharging.value) {
                    achieved.value = true
                }
            }
        }

        //registers charging Receiver
        registerReceiver(chargingReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        //chapter content
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

    //initial and final text
    val initialMessage = "A long journey you have left behind,\nMany nights awake, no rest to find.\nIt's time to lay down and let your mind mend,\nTo regain your energy, my dear friend."
    val finalMessage = "You have done it! Now you regained your energy,\nFor you can continue your path,\nstill a little along the way the treasure to see."

    //background color that changes depending on the status of achievement
    val backgroundColor by animateColorAsState(
        targetValue = if (achieved) Color.White else Color.Gray,
        animationSpec = tween(durationMillis = 2000)
    )
    //text color that changes depending on the status of achievement
    val textColor by animateColorAsState(
        targetValue = if (achieved) Color.Black else Color.DarkGray,
        animationSpec = tween(durationMillis = 2000)
    )
    //Changes boolean if phone is charging
    LaunchedEffect(isCharging) {
        if (isCharging) {
            delay(5000) // delay before changing the text
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
        //Title
        Text(
            text = "CHAPTER",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )
        //Subtitle
        Text(
            text = "Six",
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 66.dp)
        )
        //text with flashing animation
        if (showInitialText){
            AnimatedTypewriterText(
                text = initialMessage,
                fontSize = 13,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
        else {
            AnimatedTextFlashing(
                text = finalMessage,
                color = textColor
            )
        }
    }
}

//function is for previewing in the IDE
@Preview(showBackground = true)
@Composable
fun Chapter6ContentPreview() {
    VerseVerwebtTheme {
        Chapter6Content(isCharging = false, achieved = false)
    }
}


