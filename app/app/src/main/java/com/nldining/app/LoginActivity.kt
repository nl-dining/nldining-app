package com.nldining.app

import androidx.compose.ui.text.input.PasswordVisualTransformation
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nldining.app.ui.theme.NLdiningTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NLdiningTheme(
                darkTheme = false,
                dynamicColor = false // ⬅️ Forces it to use your custom `LightColorScheme
            )
            {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background // ✅ Apply theme bg
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.background) // ✅ Set bg color
                    ) {
                        LoginScreen { email ->
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), // ✅ No background here; handled by parent
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onLogin(email) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
    }
}

