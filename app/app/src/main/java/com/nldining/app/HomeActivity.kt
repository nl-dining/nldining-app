package com.nldining.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.nldining.app.models.Restaurant
import com.nldining.app.retrofit.nlDining.searchByPlaatsResponse
import com.nldining.app.retrofit.RetrofitClient as RestaurantApiClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.nldining.app.ui.theme.NLdiningTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NLdiningTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        onSignOut = {
                            FirebaseAuth.getInstance().signOut()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onSignOut: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    // Zoekvelden states
    var plaats by remember { mutableStateOf("") }
    var categorie by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var reviewScoreOverall by remember { mutableStateOf("") }
    var reviewScoreFood by remember { mutableStateOf("") }
    var reviewScoreService by remember { mutableStateOf("") }
    var reviewScoreAmbiance by remember { mutableStateOf("") }

    // Restaurant lijst en selectie states
    var restaurants by remember { mutableStateOf<List<searchByPlaatsResponse>>(emptyList()) }
    var selectedRestaurants by remember { mutableStateOf<Set<searchByPlaatsResponse>>(emptySet()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Context voor intent
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🔍 Zoek restaurants", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Zoekvelden
        OutlinedTextField(
            value = plaats,
            onValueChange = { plaats = it },
            label = { Text("Plaats") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = categorie,
            onValueChange = { categorie = it },
            label = { Text("Categorie") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tags,
            onValueChange = { tags = it },
            label = { Text("Tags") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = reviewScoreOverall,
            onValueChange = { reviewScoreOverall = it },
            label = { Text("Algemene score") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = reviewScoreFood,
            onValueChange = { reviewScoreFood = it },
            label = { Text("Eten score") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = reviewScoreService,
            onValueChange = { reviewScoreService = it },
            label = { Text("Service score") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = reviewScoreAmbiance,
            onValueChange = { reviewScoreAmbiance = it },
            label = { Text("Sfeer score") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    errorMessage = null
                    try {
                        // Update API call om alle zoekcriteria mee te sturen
                        val result = RestaurantApiClient.instance.searchRestaurants(
                            plaats = plaats,
                            categorie = categorie,
                            tags = tags,
                            reviewScoreOverall = reviewScoreOverall,
                            reviewScoreFood = reviewScoreFood,
                            reviewScoreService = reviewScoreService,
                            reviewScoreAmbiance = reviewScoreAmbiance
                        )
                        restaurants = result
                    } catch (e: HttpException) {
                        errorMessage = "Serverfout: ${e.localizedMessage}"
                    } catch (e: IOException) {
                        errorMessage = "Netwerkfout: ${e.localizedMessage}"
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zoek")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Knop om naar Maps pagina te gaan
        Button(
            onClick = {
                if (selectedRestaurants.isNotEmpty()) {
                    val intent = Intent(context, MapsActivity::class.java).apply {
                        // Serializeer de geselecteerde restaurants
                        putParcelableArrayListExtra(
                            "SELECTED_RESTAURANTS",
                            ArrayList(selectedRestaurants.toList())
                        )
                    }
                    context.startActivity(intent)
                }
            },
            enabled = selectedRestaurants.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Toon op kaart")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        } else if (restaurants.isEmpty()) {
            Text("Geen resultaten", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(restaurants) { restaurant ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = restaurant in selectedRestaurants,
                                onCheckedChange = {
                                    selectedRestaurants = if (it) {
                                        selectedRestaurants + restaurant
                                    } else {
                                        selectedRestaurants - restaurant
                                    }
                                }
                            )
                            Column {
                                Text(text = restaurant.naam, style = MaterialTheme.typography.titleMedium)
                                Text(text = restaurant.adres, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign out")
        }
    }
}