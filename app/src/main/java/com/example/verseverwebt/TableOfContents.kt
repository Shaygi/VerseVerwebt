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
import com.example.verseverwebt.ui.pages.VerseVerwebtTheme

class TableOfContents : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TableOfContentsContent()
                }
            }
        }
    }
}

@Composable
fun TableOfContentsContent() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Table of Contents",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ButtonColumn("Chapter 1") {
            context.startActivity(Intent(context, Chapter1::class.java))
        }
        ButtonColumn("Chapter 2") {
            context.startActivity(Intent(context, Chapter2::class.java))
        }
        ButtonColumn("Chapter 3") {
            context.startActivity(Intent(context, Chapter3::class.java))
        }
        ButtonColumn("Chapter 4") {
            context.startActivity(Intent(context, Chapter4::class.java))
        }
        ButtonColumn("Chapter 5") {
            context.startActivity(Intent(context, Chapter5::class.java))
        }
    }
}
