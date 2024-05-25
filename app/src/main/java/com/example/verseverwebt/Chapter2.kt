package com.example.verseverwebt

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class Chapter2 : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magneticField: Sensor? = null

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null
    private var azimuth: Float by mutableStateOf(0f)

    private var westCount = 0
    private val delay = 1000L // 1 second delay

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                when (it.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> gravity = it.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> geomagnetic = it.values.clone()
                }

                if (gravity != null && geomagnetic != null) {
                    val R = FloatArray(9)
                    val I = FloatArray(9)
                    if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                        val orientation = FloatArray(3)
                        SensorManager.getOrientation(R, orientation)
                        azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
                        if (azimuth < 0) {
                            azimuth += 360
                        }
                        // Check if the device is pointing exactly west (within ±10 degrees of 270)
                        if (azimuth in 260f..280f) {
                            westCount++
                        } else {
                            westCount = 0
                            chapter2Text.value = "Im Norden steht eine Statue starr und kalt, ihr Blick richtet sich nach Osten, doch niemals in den Süden, denn ihr Herz wird sich immer nach dem Westen sehnen."
                        }
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private val handler = Handler(Looper.getMainLooper())
    private val checkWestRunnable = object : Runnable {
        override fun run() {
            if (westCount >= 5) { // Checking if the device has been pointing west for 5 seconds
                chapter2Text.value = "gut gemacht"
            }
            westCount = 0
            handler.postDelayed(this, delay * 5)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapter2Text.value = "Im Norden steht eine Statue starr und kalt, ihr Blick richtet sich nach Osten, doch niemals in den Süden, denn ihr Herz wird sich immer nach dem Westen sehnen."
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Chapter2Content(azimuth)
                }
            }
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL) }
        magneticField?.let { sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL) }
        handler.postDelayed(checkWestRunnable, delay * 5)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
        handler.removeCallbacks(checkWestRunnable)
    }
}

private val chapter2Text = mutableStateOf("Im Norden steht eine Statue starr und kalt, ihr Blick richtet sich nach Osten, doch niemals in den Süden, denn ihr Herz wird sich immer nach dem Westen sehnen.")

@Composable
fun Chapter2Content(azimuth: Float) {
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
            text = chapter2Text.value,
            fontFamily = playfair,
            fontSize = 13.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(all = 50.dp)
        )
        Compass(azimuth)
    }
}

@Composable
fun Compass(azimuth: Float) {
    Canvas(modifier = Modifier.size(260.dp)) {
        drawIntoCanvas { canvas ->
            rotate(-azimuth, pivot = center) {
                drawLine(
                    color = Color.Red,
                    start = center,
                    end = center.copy(y = 0f),
                    strokeWidth = 8f
                )
                drawCircle(
                    color = Color.Black,
                    center = center,
                    radius = size.minDimension / 2,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Chapter2ContentPreview() {
    VerseVerwebtTheme {
        Chapter2Content(0f)
    }
}

