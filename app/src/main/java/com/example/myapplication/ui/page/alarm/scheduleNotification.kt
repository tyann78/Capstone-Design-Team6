package com.example.myapplication.ui.page.alarm

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.myapplication.ui.backendset.data.ScheduleData
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

fun scheduleWork(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val oneTimeWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setConstraints(constraints)
        .setInitialDelay(5, TimeUnit.MINUTES) // 5분 후에 실행
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "notificationWork",
        ExistingWorkPolicy.KEEP,
        oneTimeWorkRequest
    )
}
//
//fun scheduleWork(context: Context) {
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED) // 네트워크 연결 필수
//        .build()
//
//    val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(5, TimeUnit.MINUTES)
//        .setConstraints(constraints)
//        .build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//        "notificationWork",
//        ExistingPeriodicWorkPolicy.KEEP,
//        periodicWorkRequest
//    )
//}
//fun scheduleNotification(schedule: ScheduleData, context: Context) {
//    // Workerに渡すデータ
//    val inputData = workDataOf(
//        "title" to schedule.name,
//        "message" to "출발 5분 전 입니다."
//    )
//
//    // 通知の予定時間を計算
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
//    val moveStartTime = LocalDateTime.parse(schedule.startTime, formatter)
//    val notificationTime = moveStartTime.minusSeconds(schedule.expectedTravelTime + schedule.bufferTime)
//    val currentTime = LocalDateTime.now()
//
//    // 通知までの時間差を計算
//    val delay = Duration.between(currentTime, notificationTime).toMillis()
//
//    if (delay > 0) {
//        // 通知をスケジュール
//        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
//            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
//            .setInputData(inputData)  // データを渡す
//            .build()
//
//        // WorkManagerに仕事を登録
//        WorkManager.getInstance(context.applicationContext).enqueue(workRequest)
//    }
//}