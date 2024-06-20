package com.example.verseverwebt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.verseverwebt.ui.theme.CustomTypography
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme

class Credits : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //This function starts the CreditContent function and set the Conetnt for this activity
        setContent {
            VerseVerwebtTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreditsContent()
                }
            }
        }
    }
}

@Composable
fun CreditsContent() {

    //Vertical text elements and a menu button
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackToMenuButton()

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "THE",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "CREDITS",
            style = CustomTypography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 66.dp)
        )
        Text(
            text = "Developer: ",
            style = CustomTypography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Sophie Brand, Shirin Erol, Lena Müller ",
            style = CustomTypography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp, start = 50.dp, end = 50.dp)
        )
        Text(
            text = "Project leader: ",
            style = CustomTypography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = " Prof. Dr. Thorsten Teschke ",
            style = CustomTypography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp, start = 50.dp, end = 50.dp)
        )
        Text(
            text = "Use images:  ",
            style = CustomTypography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "https://www.vecteezy.com/png/8507495-lens-flare-light-special-effect \n" +
                    "https://www.vecteezy.com/png/26758496-treasure-png-with-ai-generated \n",
            style = CustomTypography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp, start = 50.dp, end = 50.dp)
        )
    }
    Seitenzahl("-80-")
}


