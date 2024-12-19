package com.example.myapplication.ui.page.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.backendset.BackendUtil
import com.example.myapplication.ui.backendset.data.ScheduleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DayScheduleViewModel : ViewModel() {
    private val _scheduleList = MutableStateFlow<List<ScheduleData>>(emptyList())
    val scheduleList: StateFlow<List<ScheduleData>> = _scheduleList

    private val backendUtil = BackendUtil()

    init {
        fetchSchedules()
    }

    fun fetchSchedules() {
        backendUtil.getScheduleData(object : BackendUtil.ScheduleDataCallback {
            override fun onSuccess(scheduleList: List<ScheduleData>) {
                _scheduleList.value = scheduleList
            }

            override fun onFailure(t: Throwable) {
                Log.e("DayScheduleViewModel", "Failed to fetch schedules", t)
            }
        })
    }

    fun deleteSchedule(idToDelete: Int) {
        backendUtil.deleteSchedule(idToDelete, object : BackendUtil.DeleteScheduleCallback {
            override fun onSuccess(message: String) {
                Log.d("DayScheduleViewModel", message)
                fetchSchedules() // 成功後にスケジュールを再取得
            }

            override fun onFailure(t: Throwable) {
                Log.e("DayScheduleViewModel", "Failed to delete schedule", t)
            }
        })
    }
}
