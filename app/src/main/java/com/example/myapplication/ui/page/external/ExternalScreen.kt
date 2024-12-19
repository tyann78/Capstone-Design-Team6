package com.example.myapplication.ui.page.external

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.BottomNavigation

@Composable
fun ExternalScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    var externalCalendarSynced by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "외부 캘린더 연동",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "다른 웹 캘린더에서 일정을 가져와 통합된 화면에서 확인 가능.",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // 外部カレンダー連携処理（例：Google カレンダー API 呼び出し）
                    externalCalendarSynced = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Google 캘린더 연동", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (externalCalendarSynced) {
                Text(
                    text = "Google 캘린더와 연동되었습니다.",
                    color = Color.Green,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 샘플 일정
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .padding(8.dp)
                ) {
                    Text("2024-11-20 14:00 - 회의", color = Color.White)
                    Text("2024-11-22 10:00 - 워크샵", color = Color.White)
                    Text("2024-11-25 09:00 - 출근", color = Color.White)
                }
            } else {
                Text(
                    text = "아직 외부 캘린더에서 일정을 가져오지 않았습니다.",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }

        // BottomNavigationを画面下に追加
        BottomNavigation(
            onHomeClick = { navController.navigate("calendar") },
            onInputFormClick = { navController.navigate("inputForm") }
        )
    }
}
