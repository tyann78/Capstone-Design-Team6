package com.example.test

// ApiService.kt
import com.example.myapplication.ui.backendset.data.BoolResponse
import com.example.myapplication.ui.backendset.data.PoiData
import com.example.myapplication.ui.backendset.data.ScheduleData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("schedules/all")
    fun getScheduleData(): Call<List<ScheduleData>>

    @POST("schedules/add")
    fun addSchedule(@Body schedule: ScheduleData): Call<BoolResponse>
    //TODO: return 값 정상 받아올 수 있게 만들기

    @DELETE("schedules/delete/id/{id}")
    fun deleteSchedule(@Path("id") id: Int): Call<Int>

    @GET("searchPOI")
    fun getPoiData(@Query("keyword") keyword: String): Call<PoiData>

    @GET("routeStaticMap")
    fun getRouteStaticMap(
        @Header("appKey") appKey: String,
        @Query("startX") startX: Double,
        @Query("startY") startY: Double,
        @Query("endX") endX: Double,
        @Query("endY") endY: Double,
        @Query("lineColor") lineColor: String,
        @Query("width") width: Int,
        @Query("height") height: Int
    ): Call<ResponseBody>
}