package com.example.myapplication.ui.page.input

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.BottomNavigation
import com.example.myapplication.ui.backendset.data.ScheduleData
import com.example.myapplication.ui.backendset.data.SharedArrivalPoi
import com.example.myapplication.ui.backendset.data.SharedDepartPoi
import com.example.myapplication.ui.backendset.data.SharedID
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TravelInputForm(
    navController: NavController,
    context: Context,
    viewModel: TravelInputViewModel = viewModel()
) {
    var departString = "출발지를 입력해 주세요"
    var arrivalString = "도착지를 입력해 주세요"
    var departColor = Color.Gray
    var arrivalColor = Color.Gray
    if (!SharedDepartPoi.empty) {
        departString = SharedDepartPoi.name
        departColor = Color.Black
    }
    if (!SharedArrivalPoi.empty) {
        arrivalString = SharedArrivalPoi.name
        arrivalColor = Color.Black
    }

    val saveState by viewModel.saveState.collectAsState()
    val selectedPOI by viewModel.selectedPOI.collectAsState()

    var name by remember { mutableStateOf("") }
    var departureDate by remember { mutableStateOf("날짜 선택") }
    var departureHour by remember { mutableStateOf("시간 선택") }
    var departureMinute by remember { mutableStateOf("분 선택") }
    var arrivalDate by remember { mutableStateOf("날짜 선택") }
    var arrivalHour by remember { mutableStateOf("시간 선택") }
    var arrivalMinute by remember { mutableStateOf("분 선택") }
    var bufferTime by remember { mutableStateOf("없음") }
    var trafficInfo by remember { mutableStateOf("없음") }
    var repeatEnabled by remember { mutableStateOf("없음") }
    var departureLocation by remember { mutableStateOf("") }
    var arrivalLocation by remember { mutableStateOf("") }
    var alarmEnabled by remember { mutableStateOf(false) }

    // 날짜 옵션 "2024-12-19T19:32:22+0900"
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()
    val dateOptions = (0..(1 * 365)).map { today.plusDays(it.toLong()).format(dateFormatter) }

    // 시간 옵션 (0시부터 23시까지)
    val hourOptions = (0..23).map { it.toString().padStart(2, '0') }

    // 분 옵션 (0분부터 59분까지)
    val minuteOptions = (0..59 step 5).map { it.toString().padStart(2, '0') }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .padding(bottom = 72.dp) // Bottom navigation height (to avoid overlap)
        ) {
            item {
                // 이름 입력
                Text(
                    text = "이름 입력",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("이름을 입력하세요", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                    textStyle = TextStyle(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF444444))
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF444444))
                            .padding(16.dp)
                    ) {
                        // 시작 시간
                        Text(
                            text = "시작 시간",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.weight(0.4f) // 40% width
                            ) {
                                DropdownMenuField(dateOptions, departureDate) { departureDate = it }
                            }
                            Box(
                                modifier = Modifier.weight(0.3f) // 30% width
                            ) {
                                DropdownMenuField(hourOptions, departureHour) { departureHour = it }
                            }
                            Box(
                                modifier = Modifier.weight(0.3f) // 30% width
                            ) {
                                DropdownMenuField(minuteOptions, departureMinute) { departureMinute = it }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // 도착 시간
                        Text(
                            text = "종료 시간",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.weight(0.4f) // 40% width
                            ) {
                                DropdownMenuField(dateOptions, arrivalDate) { arrivalDate = it }
                            }
                            Box(
                                modifier = Modifier.weight(0.3f) // 30% width
                            ) {
                                DropdownMenuField(hourOptions, arrivalHour) { arrivalHour = it }
                            }
                            Box(
                                modifier = Modifier.weight(0.3f) // 30% width
                            ) {
                                DropdownMenuField(minuteOptions, arrivalMinute) { arrivalMinute = it }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // 여유 시간
                        Text(
                            text = "미리 도착",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // 선택 가능한 미리 도착 시간 옵션
                            val bufferTimeOptions = listOf(
                                "없음", "5분", "10분", "15분", "30분", "45분", "1시간", "2시간"
                            )
                            DropdownMenuField(
                                bufferTimeOptions,
                                bufferTime
                            ) { bufferTime = it }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // 여유 시간
                        Text(
                            text = "교통 정보",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // 선택 가능한 교통 수단 옵션
                            val trafficInfoOptions = listOf(
                                "자가용", "자전거", "도보"
                            )
                            DropdownMenuField(
                                trafficInfoOptions,
                                trafficInfo
                            ) { trafficInfo = it }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // 여유 시간
                        Text(
                            text = "반복 설정",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // 선택 가능한 교통 수단 옵션
                            val repeatEnabledOptions = listOf(
                                "없음", "매일", "매주", "매월", "매년"
                            )
                            DropdownMenuField(
                                repeatEnabledOptions,
                                repeatEnabled
                            ) { repeatEnabled = it }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "출발지 입력",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                // Navigate to search screen for departure
                                navController.navigate("search/departure")
                            }
                    ) {
                        TextField(
                            value = departureLocation,
                            onValueChange = { departureLocation = it },
                            label = { Text(departString, color = departColor) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black),
                            singleLine = true,
                            enabled = false // Disable direct editing
                        )
                    }
                    IconButton(onClick = {
                        // Navigate to search screen for departure
                        navController.navigate("search/departure")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "검색",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "도착지 입력",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                // Navigate to search screen for arrival
                                navController.navigate("search/arrival")
                            }
                    ) {
                        TextField(
                            value = arrivalLocation,
                            onValueChange = { arrivalLocation = it },
                            label = { Text(arrivalString, color = arrivalColor) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black),
                            singleLine = true,
                            enabled = false // Disable direct editing
                        )
                    }
                    IconButton(onClick = {
                        // Navigate to search screen for arrival
                        navController.navigate("search/arrival")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "검색",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 알람 설정
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "알람 설정",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Switch(
                        checked = alarmEnabled,
                        onCheckedChange = { alarmEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.Green)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 설정 저장 버튼
                Button(
                    onClick = {
                        var temp:Int = 0
                        if (bufferTime == "5분") {
                            temp = 300
                        }
                        else if (bufferTime == "10분") {
                            temp = 600
                        }
                        else if (bufferTime == "15분") {
                            temp = 900
                        }
                        else if (bufferTime == "30분") {
                            temp = 1800
                        }
                        else if (bufferTime == "45분") {
                            temp = 2700
                        }
                        else if (bufferTime == "1시간") {
                            temp = 3600
                        }
                        else if (bufferTime == "2시간") {
                            temp = 7200
                        }
                        var temp2:Int = 0
                        if (trafficInfo == "자전거") {
                            temp2 = 1
                        }
                        else if (trafficInfo == "도보") {
                            temp2 = 2
                        }
                        Log.d(
                            "TEST",
                            departureDate + "T" + departureHour + ":" + departureMinute + "+0900"
                        )

                        SharedID.id += 1
                        val scheduleData = ScheduleData(
                            id = SharedID.id,
                            name = name,
                            startTime = departureDate + "T" + departureHour + ":" + departureMinute + ":00+0900",  // 修正部分
                            endTime = arrivalDate + "T" + arrivalHour + ":" + arrivalMinute + ":00+0900",          // 修正部分
                            startX = SharedDepartPoi.x,
                            startY = SharedDepartPoi.y,
                            endX = SharedArrivalPoi.x,
                            endY = SharedArrivalPoi.y,
                            bufferTime = temp,
                            transportMethod = temp2,
                            expectedTravelTime = 0,
                            alarmEnabled = alarmEnabled,
                            repeatEnabled = false //TODO
                        )
                        viewModel.saveTravelData(scheduleData, context)

                        SharedDepartPoi.empty = true
                        SharedArrivalPoi.empty = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "설정 저장", color = Color.Black)
                }

                when {
                    saveState.isSuccess -> {
                        Text(
                            text = saveState.getOrNull() ?: "저장되었습니다!",
                            color = Color.Green,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    saveState.isFailure -> {
                        val error = saveState.exceptionOrNull()?.localizedMessage ?: "저장에 실패했습니다."
                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    else -> Unit
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // BottomNavigation 추가
        BottomNavigation(
            onHomeClick = { navController.navigate("calendar") },
            onInputFormClick = { navController.navigate("inputForm") }
        )
    }
}
// DropdownMenuField 컴포저블
@Composable
fun DropdownMenuField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            label = { Text(selectedOption, color = Color.White) },
            readOnly = true,
            textStyle = TextStyle(color = Color.White)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

