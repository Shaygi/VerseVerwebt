package com.example.verseverwebt

import android.content.Context
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.verseverwebt.api.ApiClient
import com.example.verseverwebt.ui.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.toDegrees

class Chapter2 : ComponentActivity() {
    var startTime: Long = 0
    var endTime: Long = 0

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magneticField: Sensor? = null

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null

    private var azimuth by mutableStateOf(0f)
    private var westCount by mutableStateOf(0)
    private var achieved by mutableStateOf(false)

    private lateinit var onAchieved: () -> Unit

    // Listener to respond to sensor data changes
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
                        //Log.d("Chapter2", "Azimuth: $azimuth")
                        if (azimuth in 260f..280f && !achieved) westCount++ else westCount = 0
                        if (westCount >= 5) {
                            //chapter2Text.value = "gut gemacht"
                            achieved = true
                            onAchieved()
                        } else if (!achieved) {
                            chapter2Text.value = "In the North, a statue stands cold and tall,\nits gaze fixed East, never South at all,\nFor its heart forever the West does yearn,\nIn that direction, it will always turn."
                        }
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startTime = System.currentTimeMillis()

        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var showDialog by remember { mutableStateOf(false) }
                    var levelTime by remember { mutableStateOf(0L) }

                    onAchieved = {
                        levelTime = stopTimer()
                        showDialog = true
                    }

                    Chapter2Content(azimuth, showDialog, levelTime, onAchieved) { showDialog = it }
                }
            }
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    private fun stopTimer(): Long {
        endTime = System.currentTimeMillis()
        return endTime - startTime
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("startTime", startTime)
        outState.putLong("endTime", endTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        startTime = savedInstanceState.getLong("startTime")
        endTime = savedInstanceState.getLong("endTime")
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

private val chapter2Text = mutableStateOf("In the North, a statue stands cold and tall,\nits gaze fixed East, never South at all,\nFor its heart forever the West does yearn,\nIn that direction, it will always turn.")

@Composable
fun Chapter2Content(azimuth: Float, showDialog: Boolean, levelTime: Long, onAchieved: () -> Unit, updateShowDialog: (Boolean) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackToMenuButton()
        Spacer(modifier = Modifier.height(32.dp))
        // Title
        Text(
            text = "CHAPTER",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )
        // Subtitle
        Text(
            text = "Two",
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 36.dp)
        )
        // Display the current text
        AnimatedTypewriterText(
            text = chapter2Text.value,
            fontSize = 13,
            textAlign = TextAlign.Center,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(10.dp))
        // Draw the compass
        Compass(azimuth)
    }

    //The button that takes you to the next activity
    ToTheNextPage(nextClass = Chapter3::class.java, hasWin = true)

    if (showDialog) {
        val userId = getUserId(context)
        val time = levelTime.toFloat() / 1000

        ApiClient.instance.updateChapterTime(userId, 2, time).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.d("Chapter 2", "Saved time successfully")
                } else {
                    Log.e("Chapter 2", "Error with saving")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("Chapter 2", "Error")
            }
        })

        AlertDialog(
            onDismissRequest = { updateShowDialog(false) },
            confirmButton = {
                TextButton(onClick = {
                    updateShowDialog(false)
                }) {
                    Text("OK")
                }
            },
            title = { Text("Congratulations!") },
            text = { Text("You completed the chapter in ${levelTime / 1000} seconds.") }
        )
    }
}

@Composable
fun Compass(azimuth: Float) {
    Canvas(modifier = Modifier.requiredSize(280.dp)) {
        val strokeWidth = 6.dp.toPx()
        val compassRadius = size.minDimension / 2 - strokeWidth

        rotate(-azimuth, pivot = center) {
            drawCircle(
                color = Color(0xFF8B4513),
                center = center,
                radius = compassRadius + 30,
                style = Stroke(width = strokeWidth)
            )
            drawCircle(
                color = Color(0xFFD2B48C),
                center = center,
                radius = compassRadius - 30.dp.toPx(),
                style = Stroke(width = strokeWidth / 2)
            )
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

        val textPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.CENTER
            typeface = android.graphics.Typeface.create("serif", android.graphics.Typeface.BOLD)
        }
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawText("N", center.x, center.y - compassRadius + 40f, textPaint)
            canvas.nativeCanvas.drawText("E", center.x + compassRadius - 40f, center.y, textPaint)
            canvas.nativeCanvas.drawText("S", center.x, center.y + compassRadius - 10f, textPaint)
            canvas.nativeCanvas.drawText("W", center.x - compassRadius + 40f, center.y, textPaint)

            val smallTextPaint = Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 20f
                textAlign = Paint.Align.CENTER
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
        Chapter2Content(0f, false, 0L, {}, {})
    }
}
