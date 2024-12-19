package com.example.myapplication.ui.backendset.data

import android.util.Log
import com.example.myapplication.ui.backendset.BackendUtil

class SharedScheduleList {
    companion object {
        // MutableList로 변경하여 스케줄 추가 및 수정 가능
        private var scheduleList: MutableList<ScheduleData> = mutableListOf()

        // 스케줄 추가 메소드
        fun addSchedule(schedule: ScheduleData) {
            SharedID.id = schedule.id
            scheduleList.add(schedule)
        }

        // 스케줄 리스트 반환 메소드
        fun getSchedules(): List<ScheduleData> {
            updateSchedule()
            return scheduleList
        }

        // 스케줄 삭제 메소드 (인덱스로 삭제)
        fun removeSchedule(index: Int) {
            if (index in scheduleList.indices) {
                scheduleList.removeAt(index)
            }
        }

        // 스케줄 업데이트 메소드
        fun updateTravelTime(index: Int, time: Long) {
            if (index in scheduleList.indices) {
                scheduleList[index].expectedTravelTime = time
            }
        }

        private fun updateSchedule() {
            scheduleList.clear()
            // db에 있는 모든 스케줄 데이터 일단 불러오기
            val backendUtil = BackendUtil()
            // 비동기적으로 스케줄 데이터를 가져오는 함수 호출
            backendUtil.getScheduleData(object : BackendUtil.ScheduleDataCallback {
                override fun onSuccess(scheduleList: List<ScheduleData>) {
                    // 데이터를 성공적으로 받아온 경우 처리
                    Log.d("InitScheduleList", "SUCCESS")
                    // scheduleList를 이용해 UI를 업데이트하거나 다른 로직을 처리
                    for (schedule in scheduleList) {
                        addSchedule(schedule)
                    }
                }

                override fun onFailure(t: Throwable) {
                    // 데이터를 받아오는데 실패한 경우 처리
                    Log.e("ERROR", "Failed to load schedule data", t)
                }
            })
        }
    }
}