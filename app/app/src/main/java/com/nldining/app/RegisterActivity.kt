package com.nldining.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nldining.app.ui.theme.NLdiningTheme
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Voorkom screenshots en schermopnames
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        setContent {
            NLdiningTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterScreen()
                }
            }
        }
    }
    // Voorkomt tapjacking
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return if (ev.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED != 0) {
            false
        } else {
            super.dispatchTouchEvent(ev)
        }
    }
}

@Composable
fun RegisterScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var diet by remember { mutableStateOf("None") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)

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

        DietDropdown(selectedDiet = diet, onDietSelected = { diet = it })

        Button(
            onClick = {
                if (!AuthValidator.isValidEmail(email)) {
                    Toast.makeText(context, "Ongeldig e-mailadres", Toast.LENGTH_LONG).show()
                    return@Button
                }

                if (!PasswordValidator.isValid(password)) {
                    Toast.makeText(
                        context,
                        "Wachtwoord moet minstens 8 tekens bevatten, inclusief een hoofdletter, kleine letter, cijfer en speciaal teken.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@Button
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                                if (emailTask.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Account created! Please verify your email.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    context.startActivity(Intent(context, LoginActivity::class.java))
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Failed to send verification email: ${emailTask.exception?.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Registration failed: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Register")
        }
    }
}

@Composable
fun DietDropdown(selectedDiet: String, onDietSelected: (String) -> Unit) {
    val diets = listOf("None", "Vegan", "Vegetarian", "Halal", "Kosher", "Gluten-Free")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedDiet,
            onValueChange = {},
            readOnly = true,
            label = { Text("Diet preference") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            diets.forEach { diet ->
                DropdownMenuItem(
                    text = { Text(diet) },
                    onClick = {
                        onDietSelected(diet)
                        expanded = false
                    }
                )
            }
        }
    }
}

