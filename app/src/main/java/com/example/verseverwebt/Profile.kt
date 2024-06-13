package com.example.verseverwebt

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.verseverwebt.api.ApiClient
import com.example.verseverwebt.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme
import com.example.verseverwebt.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "Unknown") ?: "Unknown"
        val rank = sharedPreferences.getInt("user_rank", 0)
        val userTimesString = sharedPreferences.getString("user_times", "0,0,0,0,0") ?: "0,0,0,0,0"
        val userTimes = stringToFloatArray(userTimesString)

        setContent {
            LaunchedEffect(Unit) {
                ApiClient.instance.calculateRankings()
            }
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ProfileContent(userName, rank,  userTimes)
                }
            }
        }
    }

    private fun stringToFloatArray(string: String): FloatArray {
        return string.split(",").map { it.toFloat() }.toFloatArray()
    }
}

@Composable
fun ProfileContent(userName: String, rank: Int,  times: FloatArray) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackToMenuButton()

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Profile",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userName,
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        var timet = 0f;

        times.forEachIndexed { index, time ->
            Text(
                text = "Time for Chapter ${index + 1}: $time",
                style = CustomTypography.bodyMedium,
                textAlign = TextAlign.Center
            )
            timet += time
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total time: $timet",
            style = CustomTypography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Placement: $rank",
            style = CustomTypography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
