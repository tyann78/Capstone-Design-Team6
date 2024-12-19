package com.example.myapplication.ui.page.log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.BottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelLogScreen(
    navController: NavController
) {
    val logs = listOf(
        "2024-11-12: 출근 시간 5분 지연 - 도로 공사로 인한 교통 혼잡",
        "2024-11-10: 지하철 노선 변경으로 인해 10분 단축",
        "2024-11-08: 비로 인해 이동 시간 15분 지연",
        "2024-11-05: 주말 교통량 감소로 인해 7분 단축"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("이동 시간 변화 로그", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.Black) // Set background to dark
        ) {
            logs.forEach { log ->
                // Each log will be inside a Box with a background and rounded corners
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(8.dp)) // Rounded corners for the box
                        .padding(16.dp)
                ) {
                    Text(
                        text = log,
                        color = Color.White, // White text color
                        fontSize = 18.sp
                    )
                }
            }

            // BottomNavigation should be at the end but still scrollable with content
            Spacer(modifier = Modifier.height(56.dp)) // Add space at the bottom before the icons
        }

// Keep the BottomNavigation fixed at the bottom
        BottomNavigation(
            onHomeClick = { navController.navigate("calendar") },
            onInputFormClick = { navController.navigate("inputForm") }
        )
    }
}

