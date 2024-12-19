package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigation(
    onInputFormClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Navigationバーの高さ
                .background(Color(0xFF333333)) // 明るめの黒背景（ダークグレー）
                .padding(horizontal = 16.dp), // アイテム間のスペース
            horizontalArrangement = Arrangement.SpaceEvenly // 均等にボタンを配置
        ) {
            // Homeアイコンボタン
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier
                    .size(56.dp) // ボタンサイズ
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White, // アイコン色
                    modifier = Modifier.size(28.dp) // アイコンサイズ
                )
            }

            // Createアイコンボタン
            IconButton(
                onClick = onInputFormClick,
                modifier = Modifier
                    .size(56.dp) // ボタンサイズ
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
