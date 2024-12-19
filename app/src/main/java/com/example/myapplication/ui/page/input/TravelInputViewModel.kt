package com.example.myapplication.ui.page.input

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.backendset.BackendUtil
import com.example.myapplication.ui.backendset.RetrofitClient
import com.example.myapplication.ui.backendset.data.BoolResponse
import com.example.myapplication.ui.backendset.data.Poi
import com.example.myapplication.ui.backendset.data.ScheduleData
import com.example.myapplication.ui.backendset.data.SharedContext
import com.example.myapplication.ui.backendset.data.SharedScheduleList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TravelInputViewModel : ViewModel() {

    private val _saveState = MutableStateFlow<Result<String>>(Result.success(""))
    private val backendUtil = BackendUtil()
    val saveState: StateFlow<Result<String>> = _saveState

    private val apiService = RetrofitClient.instance

    private val _selectedPOI = MutableStateFlow<Poi?>(null)
    val selectedPOI: StateFlow<Poi?> = _selectedPOI

    fun saveTravelData(scheduleData: ScheduleData, context: Context) {
        SharedScheduleList.addSchedule(scheduleData)
        Log.d("SAVE", scheduleData.toString())
        viewModelScope.launch(Dispatchers.IO) {
            // addSchedule 메서드 호출
            backendUtil.addSchedule(scheduleData, object : BackendUtil.AddScheduleCallback {
                override fun onSuccess(boolResponse: BoolResponse) {
                    if (boolResponse.value == true) {
                        Log.d("AddSuccess", "good")  // 응답 메시지를 화면에 출력
                        Toast.makeText(context, "일정이 정상적으로 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Log.d("AddFail", "too close!")  // 응답 메시지를 화면에 출력
                        Toast.makeText(context, "이동 시간이 다른 일정을 침범합니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(boolResponse: BoolResponse) {
                    Log.d("AddError", "Failure: Something went wrong!")  // 에러 메시지를 화면에 출력
                }
            })
        }
    }

    fun saveSelectedPOI(poi: Poi) {
        _selectedPOI.value = poi
    }
}