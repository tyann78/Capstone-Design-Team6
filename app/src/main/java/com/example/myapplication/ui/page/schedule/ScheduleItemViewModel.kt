package com.example.myapplication.ui.page.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

import androidx.lifecycle.ViewModel

class ScheduleItemViewModel : ViewModel() {
    // 背景色の状態を保持
    private val _backgroundColor = mutableStateOf(Color.Cyan)
    val backgroundColor: State<Color> = _backgroundColor

    // 背景色を変更する関数
    fun changeBackgroundColor(newColor: Color) {
        _backgroundColor.value = newColor
    }
}
