package com.example.myapplication.ui.page.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.BottomNavigation
import com.example.myapplication.ui.backendset.data.SharedArrivalPoi
import com.example.myapplication.ui.backendset.data.SharedDepartPoi


@Composable
fun MapScreen(
    navController: NavController,
) {
    val viewModel: MapViewModel = viewModel()
    // Stateを取得
    val imageBitmap by viewModel.imageBitmap.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // オフセット（移動量）とスケール（ズーム）を管理する状態
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }

    // ログデータ
    val logs = listOf(
        "시간 5분 지연 - 도로 공사로 인한 교통 혼잡",
        "지하철 노선 변경으로 인해 10분 단축",
        "비로 인해 이동 시간 15분 지연",
        "주말 교통량 감소로 인해 7분 단축"
    )

    // 画像ダウンロードの開始
    LaunchedEffect(true) {
        viewModel.downloadMapImage(SharedDepartPoi.x, SharedDepartPoi.y, SharedArrivalPoi.x, SharedArrivalPoi.y)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 地図の表示部分を画面全体に広げる
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp) // ボタン領域を避けるために少し余白を追加
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        // ドラッグの動きに応じてオフセットを更新
                        offsetX += pan.x
                        offsetY += pan.y

                        // ズームの動きに応じてスケールを更新（最小1x, 最大4xまで）
                        scale = (scale * zoom).coerceIn(1f, 4f)
                    }
                }
        ) {
            // 画像がロード中の場合、ローディングインジケータを表示
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                imageBitmap?.let {
                    // ダウンロードした画像を表示し、オフセットとスケールを適用
                    Image(
                        bitmap = it,
                        contentDescription = "Map Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offsetX,
                                translationY = offsetY
                            )
                    )
                } ?: Text(
                    text = "이미지 불러오기 실패",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }

        // 画面下にBottomNavigationを追加
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            // ログをボタンのすぐ上に配置
            val log = logs.first() // ここでリストの最初のログを選択
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

            BottomNavigation(
                onHomeClick = { navController.navigate("calendar") },
                onInputFormClick = { navController.navigate("inputForm") }
            )
        }
    }
}
