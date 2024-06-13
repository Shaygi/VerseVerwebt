package com.example.verseverwebt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

class SignUp : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseVerwebtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SignUpContent()
                }
            }
        }
    }
}

@Composable
fun SignUpContent() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            style = CustomTypography.titleLarge,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(
                text = "Name",
                style = CustomTypography.bodyMedium,
            )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(
                text = "Email",
                style = CustomTypography.bodyMedium,
            )
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
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

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(
                text = "Confirm Password",
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
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                } else {
                    performSignIn(name, email, onSignUpSuccess = {
                        dialogMessage = "Account created successfully!"
                        //TODO: get mapping with findbymail, also include pw
                        showDialog = true
                    }) { error ->
                        errorMessage = error
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Sign Up",
                style = CustomTypography.bodyMedium
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(
                        text = "ok",
                        style = CustomTypography.bodyMedium
                    )
                }
            },
            text = {
                Text(
                    text = dialogMessage,
                    style = CustomTypography.bodyMedium
                )
            }
        )
    }
}

fun performSignIn(name: String, email: String, onSignUpSuccess: () -> Unit, onError: (String) -> Unit) {
    //TODO: self increment, password
    val newUser = User(1001, name, email, 0f, 0f, 0f, 0f, 0f, 0)
    ApiClient.instance.createUser(newUser).enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                onSignUpSuccess()
            } else if (response.code() == 500) {
                Log.e("SignUp", "Email already taken")
            } else {
                Log.e("SignUp", "API call failed with response code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.e("SignUp", "error")
        }
    })
}
