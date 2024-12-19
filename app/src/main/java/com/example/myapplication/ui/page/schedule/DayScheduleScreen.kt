package com.example.myapplication.ui.page.schedule

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.BottomNavigation
import com.example.myapplication.ui.backendset.data.ScheduleData
import com.example.myapplication.ui.backendset.data.SharedArrivalPoi
import com.example.myapplication.ui.backendset.data.SharedDepartPoi
import com.example.myapplication.ui.backendset.data.SharedScheduleList
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DayScheduleScreen(
    selectedDate: LocalDate,
    navController: NavController,
    viewModel: DayScheduleViewModel = viewModel()
) {
    val scheduleList = SharedScheduleList.getSchedules()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            // 日付表示
            Text(
                text = "일정: ${selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(25) { hour ->
                    // 時間を正確に扱う
                    val currentHour = if (hour == 24) {
                        LocalTime.of(0, 0)
                    } else {
                        LocalTime.of(hour, 0)
                    }
                    val nextHour = if (hour == 23) {
                        ZonedDateTime.of(selectedDate.plusDays(1), LocalTime.of(0, 0), ZoneId.systemDefault())
                    } else {
                        ZonedDateTime.of(selectedDate, currentHour.plusHours(1), ZoneId.systemDefault())
                    }

                    val currentHourStart = ZonedDateTime.of(selectedDate, currentHour, ZoneId.systemDefault())

                    val schedulesForHour = scheduleList.filter {
                        val scheduleStart = ZonedDateTime.parse(it.startTime, formatter)
                        val scheduleEnd = ZonedDateTime.parse(it.endTime, formatter)

                        val isSameDay = scheduleStart.toLocalDate() == selectedDate || scheduleEnd.toLocalDate() == selectedDate

                        val isOverlapping =
                            (scheduleStart < nextHour && scheduleEnd > currentHourStart)

                        isSameDay && isOverlapping
                    }

                    TimeSlot(
                        timeText = currentHour.format(DateTimeFormatter.ofPattern("HH:mm")),
                        schedules = schedulesForHour,
                        onDeleteSchedule = { scheduleId ->
                            viewModel.deleteSchedule(scheduleId)
                        },
                        navController = navController
                    )
                }
            }
        }

        // Bottom Navigation
        BottomNavigation(
            onHomeClick = { navController.navigate("calendar") },
            onInputFormClick = { navController.navigate("inputForm") },
        )
    }
}

@Composable
fun TimeSlot(
    timeText: String,
    schedules: List<ScheduleData>,
    onDeleteSchedule: (Int) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // 時間テキストの表示
            Text(
                text = timeText,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(60.dp) // 時間部分の幅を固定
            )
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f) // Divider を残りの幅いっぱいに広げる
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 複数の予定を Box 内に配置して重なりを表現
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 64.dp)
        ) {
            schedules.forEach { schedule ->
                ScheduleItemView(
                    schedule = schedule,
                    onDeleteSchedule = onDeleteSchedule,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ScheduleItemView(
    schedule: ScheduleData,
    onDeleteSchedule: (Int) -> Unit,
    navController: NavController,
    viewModel: ScheduleItemViewModel = viewModel() // ViewModelを取得
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
    val startTime = ZonedDateTime.parse(schedule.startTime, formatter)
    val endTime = ZonedDateTime.parse(schedule.endTime, formatter)
    val durationMinutes = java.time.Duration.between(startTime, endTime).toMinutes().toInt()

    // ViewModelから背景色を取得
    val backgroundColor = viewModel.backgroundColor.value

    // 色変更メニューの表示状態
    var expanded by remember { mutableStateOf(false) }

    // expectedTravelTime (秒) を分と時間に変換
    val hours = schedule.expectedTravelTime / 3600
    val minutes = (schedule.expectedTravelTime % 3600) / 60
    val seconds = schedule.expectedTravelTime % 60

    // フォーマットされた表示文字列
    val formattedTravelTime = if (hours > 0) {
        "$hours hours $minutes minutes"
    } else {
        "$minutes minutes"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 64.dp)
            .background(backgroundColor, shape = RoundedCornerShape(4.dp))
            .height(100.dp)
            .clickable {
                SharedDepartPoi.x = schedule.startX
                SharedDepartPoi.y = schedule.startY
                SharedArrivalPoi.x = schedule.endX
                SharedArrivalPoi.y = schedule.endY
                navController.navigate("mapScreen")
            }
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            // 予定名の上に移動時間（分と時間）のテキストを表示
            Text(
                text = "예상 이동 시간: $formattedTravelTime", // 変換された時間を表示
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            // 予定名と時間
            Text(
                text = schedule.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = "${startTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))} ~ ${endTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

        // ボタンの配置 (Column に変更)
        Column(
            modifier = Modifier.align(Alignment.TopEnd),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 削除ボタン
            IconButton(onClick = { onDeleteSchedule(schedule.id) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Schedule",
                    tint = Color.Red
                )
            }

            // 色選択ボタン (アイコンボタン)
            Box {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ColorLens,
                        contentDescription = "Change Color",
                        tint = Color.White
                    )
                }

                // 色選択メニュー
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = { viewModel.changeBackgroundColor(Color.Red); expanded = false }) {
                        Text("Red")
                    }
                    DropdownMenuItem(onClick = { viewModel.changeBackgroundColor(Color.Green); expanded = false }) {
                        Text("Green")
                    }
                    DropdownMenuItem(onClick = { viewModel.changeBackgroundColor(Color.Blue); expanded = false }) {
                        Text("Blue")
                    }
                }
            }
        }
    }
}
