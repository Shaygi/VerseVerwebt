package com.example.verseverwebt
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class Chapter2 : ComponentActivity() {
    lateinit var sensorManager: SensorManager
    lateinit var magneticSensor: Sensor
    var chapter2Text by mutableStateOf("Im Norden steht eine Statue starr und kalt, ihr Blick richtet sich nach Osten, doch niemals in den Süden, denn ihr Herz wird sich immer nach dem Westen sehnen.")
    val sensorListener = object : SensorEventListener {
        private var westCount = 0
        private val handler = Handler()
        private val delay = 5000L // 5 seconds delay

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    val magneticValues = event.values
                    val x = magneticValues[0]
                    val y = magneticValues[1]
                    val z = magneticValues[2]
                    val azimuth = Math.atan2(y.toDouble(), x.toDouble()).toFloat()
                    if (azimuth > 270 || azimuth < 90) {
                        westCount++
                        if (westCount * delay >= 5000L) {
                            // Handy hat für 5 Sekunden nach Westen gezeigt
                            chapter2Text = "gut gemacht"
                            // Reset westCount
                            westCount = 0
                        }
                    } else {
                        // Reset westCount wenn das Handy nicht nach Westen zeigt
                        westCount = 0
                        chapter2Text = "Im Norden steht eine Statue starr und kalt, ihr Blick richtet sich nach Osten, doch niemals in den Süden, denn ihr Herz wird sich immer nach dem Westen sehnen."
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        init {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    westCount = 0
                    handler.postDelayed(this, delay)
                }
            }, delay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Chapter2Content()
                }
            }
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorListener, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }
}

@Composable
fun Chapter2Content() {
    Column(
        modifier = Modifier.fillMaxSize(),
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
            text = "Two",
            fontFamily = inspiration,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = Chapter2().chapter2Text,
            fontFamily = playfair,
            fontSize = 13.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(all = 50.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.compas),
            contentDescription = "Kompass",
            tint = Color.Unspecified,
            modifier = Modifier.size(260.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Chapter2ContentPreview() {
    VerseVerwebtTheme {
        Chapter2Content()
    }
}