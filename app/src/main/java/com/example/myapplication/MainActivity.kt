package com.example.myapplication


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.example.myapplication.ui.*
import com.example.myapplication.ui.backendset.BackendUtil
import com.example.myapplication.ui.backendset.data.BoolResponse
import com.example.myapplication.ui.backendset.data.Poi_
import com.example.myapplication.ui.backendset.data.ScheduleData
import com.example.myapplication.ui.backendset.data.SharedContext
import com.example.myapplication.ui.backendset.data.SharedScheduleList
import com.example.myapplication.ui.page.alarm.scheduleWork
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        // db에 있는 모든 스케줄 데이터 일단 불러오기
        val backendUtil = BackendUtil()
        // 비동기적으로 스케줄 데이터를 가져오는 함수 호출
        backendUtil.getScheduleData(object : BackendUtil.ScheduleDataCallback {
            override fun onSuccess(scheduleList: List<ScheduleData>) {
                // 데이터를 성공적으로 받아온 경우 처리
                Log.d("InitScheduleList", "SUCCESS")
                // scheduleList를 이용해 UI를 업데이트하거나 다른 로직을 처리
                for (schedule in scheduleList) {
                    SharedScheduleList.addSchedule(schedule)
                }
                //updateUI(scheduleList)
            }

            override fun onFailure(t: Throwable) {
                // 데이터를 받아오는데 실패한 경우 처리
                Log.e("ERROR", "Failed to load schedule data", t)
            }
        })
        scheduleWork(this)
//
//        // ScheduleDTO 객체를 생성하여 서버로 전송
//        val newSchedule = ScheduleData(
//            id = 2,
//            name = "temp2",
//            startX = 126.963936,
//            startY = 37.536025,
//            endX = 129.071724,
//            endY = 35.177188,
//            startTime = "2024-12-12T18:00:00+0900",
//            endTime = "2024-12-12T19:00:00+0900",
//            bufferTime = 0,
//            transportMethod = 0,
//            expectedTravelTime = 0,
//            alarmEnabled = false,
//            repeatEnabled = false
//        )
//        // addSchedule 메서드 호출
//        backendUtil.addSchedule(newSchedule, object : BackendUtil.AddScheduleCallback {
//            override fun onSuccess(boolResponse: BoolResponse) {
//                if (boolResponse.value == true)
//                    Log.d("AddSuccess", "good")  // 응답 메시지를 화면에 출력
//                else
//                    Log.d("AddFail", "too close!")  // 응답 메시지를 화면에 출력
//            }
//
//            override fun onFailure(boolResponse: BoolResponse) {
//                Log.d("AddError", "Failure: Something went wrong!")  // 에러 메시지를 화면에 출력
//            }
//        })
//
//
//        val idToDelete = 2
//        backendUtil.deleteSchedule(idToDelete, object : BackendUtil.DeleteScheduleCallback {
//            override fun onSuccess(message: String) {
//                Log.d("Success", message)  // 성공 시 출력 메시지
//            }
//            override fun onFailure(t: Throwable) {
//                // 실패 시 에러 메시지 출력
//                Log.d("DelError", "Failure: ${t.message}")
//            }
//        })

        // "중앙대"로 POI 데이터를 요청
//        backendUtil.getPoiData("중앙대", object : BackendUtil.PoiDataCallback {
//            override fun onSuccess(poiList: List<Poi_>) {
//                if (poiList.isNotEmpty()) {
//                    poiList.forEach { poi ->
//                        Log.d("POI Info", "Name: ${poi.name}")
//                        Log.d("POI Info", "Latitude: ${poi.frontLat}")
//                        Log.d("POI Info", "Longitude: ${poi.frontLon}")
//                    }
//                } else {
//                    Log.d("POI Info", "No POIs found.")
//                }
//            }
//            override fun onFailure(t: Throwable) {
//                Log.e("POI Info", "Error: ${t.message}")
//            }
//        })


//        val startX = 126.9779692
//        val startY = 37.566535
//        val endX = 129.075986
//        val endY = 35.179554
//        backendUtil.downloadImage(
//            startX, startY, endX, endY,
//            object : BackendUtil.ImageDownloadCallback {
//                override fun onSuccess(bitmap: Bitmap) {
//                    // 성공적으로 다운로드된 이미지를 ImageView에 표시
//                    //imageView.setImageBitmap(bitmap) //////////////////////////////////////
//                    Log.d("Image Download", "Image displayed on screen")
//                }
//
//                override fun onFailure(t: Throwable) {
//                    Log.e("Image Download", "Error: ${t.message}")
//                }
//            }
//        )
        setContent {
            MyApplicationTheme {
                MainScreen(this)
            }
        }
    }
}
