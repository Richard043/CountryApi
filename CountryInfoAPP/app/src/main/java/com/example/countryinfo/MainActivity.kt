package com.example.countryinfo

import android.os.Bundle
import androidx.compose.ui.text.font.FontWeight
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import com.example.countryinfo.ui.theme.CountryinfoTheme
import com.example.countryinfo.viewmodel.CountryInfoViewModel
import androidx.navigation.NavController
import androidx.compose.ui.Alignment



class MainActivity : ComponentActivity() {
    private val viewModel: CountryInfoViewModel by viewModels()
    private var showCountryInfoScreen by mutableStateOf(true)  // State variable to control visibility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryinfoTheme {
                MyAppNavHost(viewModel)
            }

        }
    }
}

@Composable
fun MyAppNavHost(viewModel: CountryInfoViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeView(navController = navController,
            onFetchRandomCountry = {
                viewModel.fetchRandomCountry()
                navController.navigate("detail")
            },
            onFetchCountryByName = { countryName ->
                viewModel.fetchCountryByName(countryName)
                navController.navigate("detail")

            }
        )
        }
        composable("detail") { CountryInfoScreen(navController = navController, viewModel) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryInfoScreen(navController: NavController, viewModel: CountryInfoViewModel) {
    val country by viewModel.country.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = "https://i.pinimg.com/736x/0d/eb/9e/0deb9ef5f3dbc8c00422889d8eb32062.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (country == null) {
                Text("No country data available.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Please search or fetch a random country.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(text = "Back", fontSize = 18.sp, color = Color.White)
                }
            } else {
                country?.let {
                    Text(
                        text = "Country: ${it.country}",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                    Text("Country Code: ${it.countryCode}", color = Color.White)
                    Text("Continent: ${it.continent}", color = Color.White)
                    Text("Capital: ${it.capital}", color = Color.White)
                    Text("Population: ${it.population}", color = Color.White)
                    Text("Timezones:", color = Color.White)
                    Text(" ${it.timezones.joinToString(", ")}", color = Color.White)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Currency Details:",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                    Text("Code: ${it.currency.code}", color = Color.White)
                    Text("Name: ${it.currency.name}", color = Color.White)
                    Text("Symbol: ${it.currency.symbol}", color = Color.White)

                    Spacer(modifier = Modifier.height(16.dp))

                    AsyncImage(
                        model = it.flagUrl,
                        contentDescription = "Country Flag",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                    ) {
                        Text(text = "Back", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

