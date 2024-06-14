package com.example.verseverwebt

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
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
import kotlinx.coroutines.launch

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

//Text animation function that fades in the text
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

//Text animation function with a typewriter effect
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

//Text animation function that fades in the text with a delay
@Composable
fun DelayedFadeInText(text: String,
                      fontSize: Int,
                      textAlign: TextAlign,
                      color: Color,
                      modifier: Modifier = Modifier,
                      delayMillis: Int = 50
) {
    var displayText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        for (i in text.indices) {
            displayText = text.substring(0, i + 1)
            delay(delayMillis.toLong())
        }
    }

    Text(text = displayText,
        fontFamily = playfair,
        style = MaterialTheme.typography.bodySmall,
        fontSize = fontSize.sp,
        textAlign = textAlign,
        modifier = modifier.padding(all = 50.dp),
        color = color)
}

//Text animation function that slides in the text
@Composable
fun SlideInText(text: String,
                fontSize: Int,
                textAlign: TextAlign,
                color: Color,
) {
    val offsetX = remember { Animatable(-1000f) } // Starting offset position

    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Text(
        text = text,
        fontFamily = playfair,
        style = MaterialTheme.typography.bodySmall,
        fontSize = fontSize.sp,
        textAlign = textAlign,
        modifier = Modifier.offset(x = offsetX.value.dp),
        color = color
    )
}

//Text animation function that scales in the text
@Composable
fun ScaleInText(text: String,
                fontSize: Int,
                textAlign: TextAlign,
                color: Color,
) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Text(
        text = text,
        fontFamily = playfair,
        style = MaterialTheme.typography.bodySmall,
        fontSize = fontSize.sp,
        textAlign = textAlign,
        modifier = Modifier.scale(scale.value),
        color = color
    )
}

//Text animation function that fades and slides in the text
@Composable
fun FadeSlideInText(text: String,
                    fontSize: Int,
                    textAlign: TextAlign,
                    color: Color,
) {
    val alpha = remember { Animatable(0f) }
    val offsetX = remember { Animatable(-100f) }

    LaunchedEffect(Unit) {
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
        launch {
            offsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
    }

    Text(
        text = text,
        fontFamily = playfair,
        style = MaterialTheme.typography.bodySmall,
        fontSize = fontSize.sp,
        textAlign = textAlign,
        modifier = Modifier.alpha(alpha.value).offset(x = offsetX.value.dp),
        color = color
    )
}

