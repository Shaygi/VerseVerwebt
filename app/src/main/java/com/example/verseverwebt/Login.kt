package com.example.verseverwebt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.verseverwebt.api.ApiClient
import com.example.verseverwebt.theme.CustomTypography
import com.example.verseverwebt.ui.theme.Pink80
import com.example.verseverwebt.ui.theme.VerseVerwebtTheme
import com.example.verseverwebt.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoginContent(onLoginSuccess = {
                        // TODO: Navigate to Profile Activity on successful login
                        Log.d("Ranking", "success")
                        finish()
                    })
                }
            }
        }
    }
}

@Composable
fun LoginContent(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            text = "Login",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(
                text = "Username",
                style = CustomTypography.bodyMedium,
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(
                text = "Password",
                style = CustomTypography.bodyMedium,
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        if (errorMessage != null) {
            Text(text = errorMessage!!, color = Pink80, modifier = Modifier.padding(top = 16.dp))
        }

        Button(
            onClick = {
                performLogin(username, password.toString(), onLoginSuccess) { errorMessage = it }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Login",
                style = CustomTypography.bodyMedium,
            )
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, SignUp::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Sign up",
                style = CustomTypography.bodyMedium,
            )
        }
    }
}

fun performLogin(username: String, password: String, onLoginSuccess: () -> Unit, onError: (String) -> Unit) {
    ApiClient.instance.getUsers().enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            if (response.isSuccessful) {
                val users = response.body() ?: emptyList()
                val user = users.find { it.name == username && it.id.toString() == password }
                if (user != null) {
                    onLoginSuccess()
                } else {
                    Log.e("Login", "Invalid username or password")
                }
            } else {
                Log.e("Login", "API call failed with response code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            Log.e("Login", "Error fetching users: ${t.message}")
        }
    })
}
