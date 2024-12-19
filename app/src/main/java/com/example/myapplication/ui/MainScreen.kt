package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.page.external.ExternalScreen
import com.example.myapplication.ui.page.input.SearchScreen
import com.example.myapplication.ui.page.input.SearchViewModel
import com.example.myapplication.ui.page.input.TravelInputForm
import com.example.myapplication.ui.page.log.TravelLogScreen
import com.example.myapplication.ui.page.map.MapScreen
import com.example.myapplication.ui.page.schedule.DayScheduleScreen
import java.time.LocalDate

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    var alarmIsActive by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                navController = navController,
                onCalendarIntegrationClick = {
                    navController.navigate("externalScreen")
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = if (alarmIsActive) "alarmStop" else "calendar"
            ) {
                composable("calendar") {
                    CalendarScreen(
                        modifier = Modifier.fillMaxSize(),
                        onDateSelected = { date ->
                            navController.navigate("daySchedule/$date")
                        }
                    )
                    BottomNavigation(
                        onHomeClick = { navController.navigate("calendar") },
                        onInputFormClick = { navController.navigate("inputForm") }
                    )
                }

                composable("inputForm") {
                    TravelInputForm(navController = navController, context)
                }

                composable("logScreen") {
                    TravelLogScreen(navController = navController)
                }

                composable("externalScreen") {
                    ExternalScreen(navController = navController)
                }

                composable("mapScreen") {
                    MapScreen(navController = navController)
                }

                // Search Screen Route
                composable("search/{type}") { backStackEntry ->
                    val type = backStackEntry.arguments?.getString("type") ?: "unknown"
                    val searchViewModel: SearchViewModel = viewModel()
                    SearchScreen(
                        type = type,
                        navController = navController,
                        viewModel = searchViewModel,
                        onItemSelected = { selectedPoi ->
                            Log.d("SelectedPoi", "Name: ${selectedPoi.name}, Lat: ${selectedPoi.frontLat}, Lon: ${selectedPoi.frontLon}")
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    route = "daySchedule/{selectedDate}",
                    arguments = listOf(navArgument("selectedDate") { type = NavType.StringType })
                ) { backStackEntry ->
                    val selectedDate = backStackEntry.arguments?.getString("selectedDate")?.let { LocalDate.parse(it) }
                    if (selectedDate != null) {
                        DayScheduleScreen(
                            selectedDate = selectedDate,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
