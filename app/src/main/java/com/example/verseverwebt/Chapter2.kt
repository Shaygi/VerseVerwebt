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
        modifier = Modifier.fillMaxSize().padding(16.dp),
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
            text = "Two",
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = chapter2Text.value,
            style = CustomTypography.bodyMedium,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Compass(azimuth)
    }
}


@Composable
fun Compass(azimuth: Float) {
    Canvas(modifier = Modifier.size(300.dp)) {
        val strokeWidth = 6.dp.toPx()
        val compassRadius = size.minDimension / 2 - strokeWidth

        // Rotating the canvas to match the azimuth
        rotate(-azimuth, pivot = center) {
            // Drawing the outer circle of the compass
            drawCircle(
                color = Color(0xFF8B4513),
                center = center,
                radius = compassRadius + 30,
                style = Stroke(width = strokeWidth)
            )

            // Drawing the inner circle
            drawCircle(
                color = Color(0xFFD2B48C),
                center = center,
                radius = compassRadius - 30.dp.toPx(),
                style = Stroke(width = strokeWidth / 2)
            )

            // Drawing the compass needle (red for north, gray for south)
            drawLine(
                color = Color.Red,
                start = center,
                end = center.copy(y = center.y - compassRadius + 30.dp.toPx()),
                strokeWidth = 8f
            )
            drawLine(
                color = Color.Gray,
                start = center,
                end = center.copy(y = center.y + compassRadius - 30.dp.toPx()),
                strokeWidth = 8f
            )
        }

        // Drawing the cardinal points
        val textPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 40f
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create("serif", android.graphics.Typeface.BOLD)
        }

        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawText("N", center.x, center.y - compassRadius + 40f, textPaint)
            canvas.nativeCanvas.drawText("E", center.x + compassRadius - 40f, center.y, textPaint)
            canvas.nativeCanvas.drawText("S", center.x, center.y + compassRadius - 10f, textPaint)
            canvas.nativeCanvas.drawText("W", center.x - compassRadius + 40f, center.y, textPaint)

            // Drawing intermediate directions
            val smallTextPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 20f
                textAlign = android.graphics.Paint.Align.CENTER
                typeface = android.graphics.Typeface.create("serif", android.graphics.Typeface.BOLD)
            }

            canvas.nativeCanvas.drawText("NE", center.x + compassRadius * 0.707f, center.y - compassRadius * 0.707f + 20f, smallTextPaint)
            canvas.nativeCanvas.drawText("SE", center.x + compassRadius * 0.707f, center.y + compassRadius * 0.707f - 10f, smallTextPaint)
            canvas.nativeCanvas.drawText("SW", center.x - compassRadius * 0.707f, center.y + compassRadius * 0.707f - 10f, smallTextPaint)
            canvas.nativeCanvas.drawText("NW", center.x - compassRadius * 0.707f, center.y - compassRadius * 0.707f + 20f, smallTextPaint)
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



