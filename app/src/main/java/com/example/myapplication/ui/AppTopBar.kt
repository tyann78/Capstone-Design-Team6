package com.example.myapplication.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavController,
    onCalendarIntegrationClick: () -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val title = when {
        currentDestination?.route == "inputForm" -> "Input"
        currentDestination?.route == "logScreen" -> "Log"
        currentDestination?.route == "externalScreen" -> "External"
        currentDestination?.route == "mapScreen" -> "Map"
        currentDestination?.route?.startsWith("daySchedule") == true -> "Schedule"
        else -> "Calendar"
    }

    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        actions = {
            IconButton(onClick = onCalendarIntegrationClick) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = "Calendar Integration",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
        navigationIcon = {
            if (currentDestination?.route != "calendar") {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(  // Use Icon instead of Text for navigation button
                        imageVector = Icons.Default.ArrowBack,  // Default back arrow icon
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)  // Adjust size as needed
                    )
                }
            }
        }
    )
}