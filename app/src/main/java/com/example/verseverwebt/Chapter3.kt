package com.example.verseverwebt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.verseverwebt.ui.theme.CustomTypography
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackToMenuButton()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Chapter 3",
            style = CustomTypography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Your chapter content here
    }
}

@Preview(showBackground = true)
@Composable
fun Chapter3ContentPreview() {
    VerseVerwebtTheme {
        Chapter3Content()
    }
}
