package com.example.verseverwebt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

val inspiration = FontFamily(
    Font(R.font.inspiration_regular)
)
val playfair = FontFamily(
    Font(R.font.playfair_display)
)

//Main menu for navigation through menu elements
class MainMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }

    @Composable
    fun Content() {
        val context = LocalContext.current


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Woven verses",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 45.sp,
                fontFamily = playfair,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "A poetic adventure",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 30.sp,
                fontFamily = inspiration,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

            logUserId = sharedPreferences.getLong("user_id", 0L)

            if (isLoggedIn) {
                ButtonColumn("-Start-", 18.sp) {
                    context.startActivity(Intent(context, Chapter1::class.java))
                }

                ButtonColumn("Contents", 18.sp) {
                    context.startActivity(Intent(context, TableOfContents::class.java))
                }

                ButtonColumn("Leaderboard", 18.sp) {
                    context.startActivity(Intent(context, Ranking::class.java))
                }

                ButtonColumn("Profile", 18.sp) {
                    context.startActivity(Intent(context, Profile::class.java))
                }

                ButtonColumn("Logout", 18.sp) {
                    with(sharedPreferences.edit()) {
                        putBoolean("is_logged_in", false)
                        apply()
                    }
                    (context as? MainMenu)?.recreate()
                }
            } else {
                ButtonColumn("Login", 18.sp) {
                    context.startActivity(Intent(context, Login::class.java))
                }
            }

            ButtonColumn("Credits", 18.sp) {
                context.startActivity(Intent(context, Credits::class.java))
            }
        }

        LaunchedEffect(Unit) {
            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPreferences.getBoolean("is_logged_in", false)
        }
    }

    //function is for previewing in the IDE
    @Preview(showBackground = true)
    @Composable
    fun ContentPreview() {
        VerseVerwebtTheme {
            Content()

        }
    }
}
