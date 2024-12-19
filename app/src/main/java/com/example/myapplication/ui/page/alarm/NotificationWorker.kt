package com.example.myapplication.ui.page.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.ui.backendset.data.ScheduleData
import com.example.myapplication.ui.backendset.data.SharedScheduleList
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("Worker", "doWork")
        // 리스트 순회하며 현재 시각과 비교
        SharedScheduleList.getSchedules().forEach { schedule ->
            if (getDurationBetweenTimes(schedule)) {
                // 알림 생성 및 전송 로직
                showNotification(applicationContext, "출발 알림", ": 곧 출발해야 합니다.")
            }
        }
        //showNotification(applicationContext, "출발 알림", ": 곧 출발해야 합니다.")
        // 작업 완료 후 5분 후에 다시 작업을 예약
        scheduleNextWork(applicationContext)

        return Result.success()
    }


    private fun showNotification(context: Context, title: String, text: String) {

        val channelId = "your_channel_id"
        val channelName = "Your Channel Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)


        // NotificationCompat.Builder를 사용하여 알림 생성
        val notificationBuilder = NotificationCompat.Builder(context, "your_channel_id")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_notification_overlay) // 適切なアイコンを指定
        //.setAutoCancel(true) // 알림 클릭 시 자동 삭제

        notificationManager.notify(100, notificationBuilder.build()) // notificationId는 고유한 값으로 설정
    }

    private fun sendNotification(title: String, message: String) {
        Log.d("TEST", "sendNotification called")


        val context = applicationContext
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel("my_channel_id", "My Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

    //    val intent = Intent(context, MainActivity::class.java) // 클릭 시 이동할 Activity
   //     val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, "my_channel_id")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_notification_overlay) // 適切なアイコンを指定
            //.setContentIntent(pendingIntent) // 알림 클릭 시 실행
            .build()

        notificationManager.notify(1, notification)
//        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
//
//        val notificationId = 1
//        val notification = NotificationCompat.Builder(context, "default")
//            .setContentTitle(title)
//            .setContentText(message)
//            .setSmallIcon(android.R.drawable.ic_notification_overlay) // 適切なアイコンを指定
//            .build()
//
//        notificationManager.notify(notificationId, notification)
    }

    private fun scheduleNextWork(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val nextWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constraints)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "notificationWork",
            ExistingWorkPolicy.REPLACE,
            nextWorkRequest
        )
    }
}

fun getDurationBetweenTimes(schedule : ScheduleData): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    val moveStartTime = LocalDateTime.parse(schedule.startTime, formatter)
    val notificationTime = moveStartTime.minusSeconds(schedule.expectedTravelTime + schedule.bufferTime)
    val currentTime = LocalDateTime.now()

    // 通知までの時間差を計算
    val delay = Duration.between(currentTime, notificationTime).toMillis()

    return true
    //return (delay < 5 * 60 * 1000L) && notificationTime.isAfter(currentTime)
}