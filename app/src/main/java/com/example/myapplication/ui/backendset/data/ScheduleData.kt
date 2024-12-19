package com.example.myapplication.ui.backendset.data

data class ScheduleData(
    val id: Int,
    val name: String,
    val startX: Double,
    val startY: Double,
    val endX: Double,
    val endY: Double,
    val startTime: String,
    val endTime: String,
    val bufferTime: Int,
    val transportMethod: Int,
    var expectedTravelTime: Long,
    val alarmEnabled: Boolean,
    val repeatEnabled: Boolean
)
