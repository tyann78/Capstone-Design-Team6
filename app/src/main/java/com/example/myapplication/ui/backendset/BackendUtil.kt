package com.example.myapplication.ui.backendset

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.myapplication.ui.backendset.data.BoolResponse
import com.example.myapplication.ui.backendset.data.PoiData
import com.example.myapplication.ui.backendset.data.Poi_
import com.example.myapplication.ui.backendset.data.RetrofitClientSkt
import com.example.myapplication.ui.backendset.data.ScheduleData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BackendUtil {

    // 콜백 인터페이스 정의
    interface ScheduleDataCallback {
        fun onSuccess(scheduleList: List<ScheduleData>)
        fun onFailure(t: Throwable)
    }

    // 콜백을 통해 비동기 처리
    fun getScheduleData(callback: ScheduleDataCallback) {
        RetrofitClient.instance.getScheduleData().enqueue(object : Callback<List<ScheduleData>> {
            override fun onResponse(
                call: Call<List<ScheduleData>>,
                response: Response<List<ScheduleData>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    // 성공적으로 데이터를 받아왔을 때 콜백 호출
                    callback.onSuccess(response.body()!!)
                } else {
                    // 실패 시 빈 리스트 전달
                    callback.onSuccess(emptyList())
                }
            }

            override fun onFailure(call: Call<List<ScheduleData>>, t: Throwable) {
                Log.d("ERROR", "getScheduleData failed")
                // 실패 시 콜백의 onFailure 호출
                callback.onFailure(t)
            }
        })
    }

    // 콜백 인터페이스 정의
    interface AddScheduleCallback {
        fun onSuccess(boolResponse: BoolResponse)
        fun onFailure(boolResponse: BoolResponse)
    }

    // 새로운 일정 데이터를 서버에 추가하는 함수
    fun addSchedule(newSchedule: ScheduleData, callback: AddScheduleCallback) {
        RetrofitClient.instance.addSchedule(newSchedule).enqueue(object : Callback<BoolResponse> {
            override fun onResponse(call: Call<BoolResponse>, response: Response<BoolResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // 서버 응답에서 BoolResponse를 가져옴
                    callback.onSuccess(response.body()!!)
                } else {
                    // 서버 응답이 실패한 경우 false를 가진 BoolResponse 전달
                    val boolResponse = BoolResponse().apply { value = false }
                    callback.onFailure(boolResponse)
                }
            }

            override fun onFailure(call: Call<BoolResponse>, t: Throwable) {
                // 네트워크 요청 실패 시 false를 가진 BoolResponse 전달
                val boolResponse = BoolResponse().apply { value = false }
                callback.onFailure(boolResponse)
            }
        })
    }


    // 콜백 인터페이스 정의
    interface DeleteScheduleCallback {
        fun onSuccess(message: String)
        fun onFailure(t: Throwable)
    }

    // 일정 삭제 함수
    fun deleteSchedule(idToDelete: Int, callback: DeleteScheduleCallback) {
        RetrofitClient.instance.deleteSchedule(idToDelete).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result == 1) {
                        // 삭제 성공 시 메시지 전달
                        callback.onSuccess("Schedule Deleted Successfully")
                    } else {
                        // 삭제 실패 시 메시지 전달
                        callback.onSuccess("Failed to Delete Schedule")
                    }
                } else {
                    // 오류 메시지 전달
                    callback.onSuccess("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                // 실패 시 콜백 전달
                callback.onFailure(t)
            }
        })
    }

    // 콜백 인터페이스 정의
    interface PoiDataCallback {
        fun onSuccess(poiList: List<Poi_>)
        fun onFailure(t: Throwable)
    }

    // POI 데이터를 비동기적으로 가져오는 함수
    fun getPoiData(query: String, callback: PoiDataCallback) {
        RetrofitClient.instance.getPoiData(query).enqueue(object : Callback<PoiData> {
            override fun onResponse(call: Call<PoiData>, response: Response<PoiData>) {
                if (response.isSuccessful) {
                    val poiResponse = response.body()
                    poiResponse?.searchPoiInfo?.pois?.poiList?.let { poiList ->
                        // 성공적으로 데이터를 받아왔을 때 콜백 호출
                        if (poiList.isNotEmpty()) {
                            callback.onSuccess(poiList)
                        } else {
                            // POI가 없을 경우 빈 리스트를 전달
                            callback.onSuccess(emptyList())
                        }
                    } ?: run {
                        // POI 리스트가 없으면 빈 리스트 전달
                        callback.onSuccess(emptyList())
                    }
                } else {
                    Log.e("POI Info", "Failed to retrieve data: ${response.message()}")
                    // 실패 시 콜백의 onFailure 호출
                    callback.onFailure(Throwable("Failed to retrieve data"))
                }
            }

            override fun onFailure(call: Call<PoiData>, t: Throwable) {
                Log.e("POI Info", "Error: ${t.message}")
                // 실패 시 콜백의 onFailure 호출
                callback.onFailure(t)
            }
        })
    }

    // 이미지 다운로드를 위한 콜백 인터페이스 정의
    interface ImageDownloadCallback {
        fun onSuccess(bitmap: Bitmap)
        fun onFailure(t: Throwable)
    }

    // Retrofit을 이용하여 이미지를 다운로드하는 함수
    fun downloadImage(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        callback: ImageDownloadCallback
    ) {
        val appKey = "Lh2JkuXZCN1YVBQbggDv36wc1QCrDtcr8wDe0DkW"
        val lineColor = "red"
        val width = 512
        val height = 512

        RetrofitClientSkt.instance.getRouteStaticMap(
            appKey,
            startX,
            startY,
            endX,
            endY,
            lineColor,
            width,
            height
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Image Download", "Error: ${t.message}")
                callback.onFailure(t)  // 실패 시 콜백 호출
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 받은 데이터를 Bitmap으로 변환
                    val body = response.body()
                    body?.let {
                        val bitmap = BitmapFactory.decodeStream(it.byteStream())
                        callback.onSuccess(bitmap)  // 성공 시 콜백 호출
                    }
                } else {
                    Log.e("Image Download", "Failed to get image: ${response.message()}")
                    callback.onFailure(Exception("Failed to get image"))
                }
            }
        })
    }
}
