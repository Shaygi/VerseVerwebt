package com.example.verseverwebt

import android.content.Context
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme
import java.lang.Math.toDegrees

class Chapter2 : ComponentActivity() {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magneticField: Sensor? = null

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null
    private var azimuth by mutableStateOf(0f)

    private var westCount = 0
    private val delay = 1000L
    private var achieved = false

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                when (it.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> gravity = it.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> geomagnetic = it.values.clone()
                }
                if (gravity != null && geomagnetic != null) {
                    val r = FloatArray(9)
                    if (SensorManager.getRotationMatrix(r, null, gravity, geomagnetic)) {
                        val orientation = FloatArray(3)
                        SensorManager.getOrientation(r, orientation)
                        azimuth = toDegrees(orientation[0].toDouble()).toFloat().let { if (it < 0) it + 360 else it }
                        if (azimuth in 260f..280f && !achieved) westCount++ else westCount = 0
                        if (westCount >= 5) {
                            chapter2Text.value = "gut gemacht"
                            achieved = true
                        } else if (!achieved) {
                            chapter2Text.value = "Im Norden steht eine Statue starr und kalt, ihr Blick richtet sich nach Osten, doch niemals in den Süden, denn ihr Herz wird sich immer nach dem Westen sehnen."
                        }
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
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
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Two",
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = chapter2Text.value,
            style = CustomTypography.bodyMedium,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(all = 50.dp)
        )
        Compass(azimuth)
    }
}

@Composable
fun Compass(azimuth: Float) {
    Canvas(modifier = Modifier.size(260.dp)) {
        rotate(-azimuth, pivot = center) {
            // Drawing compass rose

            // Drawing compass needle
            drawLine(
                color = Color.Red,
                start = center,
                end = center.copy(y = center.y - size.minDimension / 2 + 10),
                strokeWidth = 8f
            )
            drawLine(
                color = Color.Gray,
                start = center,
                end = center.copy(y = center.y + size.minDimension / 2 - 10),
                strokeWidth = 8f
            )
            drawCircle(
                color = Color.Black,
                center = center,
                radius = size.minDimension / 2,
                style = Stroke(width = 4f)
            )
            drawCircle(
                color = Color.Black,
                center = center,
                radius = 10f,
                style = Stroke(width = 4f)
            )
        }
        // Drawing compass directions
        drawIntoCanvas {
            it.nativeCanvas.apply {
                val textPaint = Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 40f
                    textAlign = Paint.Align.CENTER
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
                drawText("N", center.x, center.y - size.minDimension / 2 + 60, textPaint)
                drawText("E", center.x + size.minDimension / 2 - 60, center.y, textPaint)
                drawText("S", center.x, center.y + size.minDimension / 2 - 20, textPaint)
                drawText("W", center.x - size.minDimension / 2 + 60, center.y, textPaint)
                val smallTextPaint = Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 20f
                    textAlign = Paint.Align.CENTER
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
                drawText("NE", center.x + size.minDimension / 2 * 0.707f - 30, center.y - size.minDimension / 2 * 0.707f + 30, smallTextPaint)
                drawText("SE", center.x + size.minDimension / 2 * 0.707f - 30, center.y + size.minDimension / 2 * 0.707f - 10, smallTextPaint)
                drawText("SW", center.x - size.minDimension / 2 * 0.707f + 30, center.y + size.minDimension / 2 * 0.707f - 10, smallTextPaint)
                drawText("NW", center.x - size.minDimension / 2 * 0.707f + 30, center.y - size.minDimension / 2 * 0.707f + 30, smallTextPaint)
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



