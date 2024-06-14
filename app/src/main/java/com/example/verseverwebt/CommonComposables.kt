package com.example.verseverwebt

import android.content.Intent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

//Creates buttons with the same style
@Composable
fun ButtonColumn(primaryText: String, fontSize: TextUnit, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = primaryText,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = playfair,
                fontSize = fontSize
            )
        }
    }
}

//Button with an icon that is reused in every Chapter
//Leads to main menu
@Composable
fun BackToMenuButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { context.startActivity(Intent(context, MainMenu::class.java)) }
        ) {
            //Button icon
            Icon(
                painter = painterResource(id = R.drawable.bookmark_icon), //path to icon
                contentDescription = "Back to Main Menu",
                tint = Color.Unspecified //ensures that icon does not change color
            )
        }
    }
}

@Composable
fun AnimatedFadeInText(
    text: String,
    fontSize: Int,
    textAlign: TextAlign,
    color: Color,
    modifier: Modifier = Modifier
) {
    var displayedText by remember { mutableStateOf("") }
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(text) {
        displayedText = ""
        currentIndex = 0
        for (char in text) {
            displayedText += char
            currentIndex++
            delay(50) // Adjust the delay to control the typing speed
        }
    }

    Row(modifier = modifier.padding(all = 50.dp)) {
        text.forEachIndexed { index, char ->
            val alpha = animateFloatAsState(
                targetValue = if (index < currentIndex) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 1000, // Duration of the fade-in effect
                    easing = LinearEasing
                )
            )
            Text(
                text = char.toString(),
                fontFamily = playfair,
                style = MaterialTheme.typography.bodySmall,
                fontSize = fontSize.sp,
                textAlign = textAlign,
                color = color.copy(alpha = alpha.value)
            )
        }
    }
}

@Composable
fun AnimatedTypewriterText(
    text: String,
    fontSize: Int,
    textAlign: TextAlign,
    color: Color,
    modifier: Modifier = Modifier
) {
    var displayedText by remember { mutableStateOf("") }
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(text) {
        displayedText = ""
        currentIndex = 0
        for (char in text) {
            displayedText += char
            currentIndex++
            delay(50) // Adjust the delay to control the typing speed
        }
    }

    val visibleChars = displayedText.mapIndexed { index, char ->
        val alpha = animateFloatAsState(if (index < currentIndex) 1f else 0f)
        char to alpha.value
    }

    Text(
        text = buildAnnotatedString {
            visibleChars.forEach { (char, alpha) ->
                withStyle(style = SpanStyle(color = color.copy(alpha = alpha))) {
                    append(char)
                }
            }
        },
        fontFamily = playfair,
        style = MaterialTheme.typography.bodySmall,
        fontSize = fontSize.sp,
        textAlign = textAlign,
        modifier = modifier.padding(all = 50.dp),
        color = color
    )
}
