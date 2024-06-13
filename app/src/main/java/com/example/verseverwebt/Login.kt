package com.example.verseverwebt

import android.content.Context
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
                    LoginContent(onLoginSuccess = { user ->
                        saveLoginState(user)
                        navigateToMainMenu()
                    })
                }
            }
        }
    }

    private fun saveLoginState(user: User) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("is_logged_in", true)
            putString("user_name", user.name)
            putInt("user_rank", user.rank)
            putString("user_times", userTimesToString(user))
            apply()
        }
    }

    private fun userTimesToString(user: User): String {
        return floatArrayOf(user.time1, user.time2, user.time3, user.time4, user.time5).joinToString(",")
    }

    private fun navigateToMainMenu() {
        val intent = Intent(this, MainMenu::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun LoginContent(onLoginSuccess: (User) -> Unit) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
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


        Button(
            onClick = {
                performLogin(username, password, onLoginSuccess = { user ->
                    dialogMessage = "Login successful!"
                    onLoginSuccess(user)
                    showDialog = true
                }) { dialogMessage = it }
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
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "OK", style = CustomTypography.bodyMedium)
                }
            },
            text = {
                Text(text = dialogMessage, style = CustomTypography.bodyMedium)
            }
        )
    }
}

fun performLogin(username: String, password: String, onLoginSuccess: (User) -> Unit, onError: (String) -> Unit) {
    ApiClient.instance.getUsers().enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            if (response.isSuccessful) {
                val users = response.body() ?: emptyList()
                val user = users.find { it.name == username && it.id.toString() == password }
                if (user != null) {
                    onLoginSuccess(user)
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
