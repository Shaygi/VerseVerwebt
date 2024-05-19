package com.example.verseverwebt

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class Chapter3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Chapter3Content()
                }
            }
        }
    }
}

@Composable
fun Chapter3Content() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { context.startActivity(Intent(context, MainMenu::class.java)) },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Back to Main Menu")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Chapter 3",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        // Add more UI elements specific to Chapter 2
    }
}

