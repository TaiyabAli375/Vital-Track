package com.myexample.vitaltrack

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters

class RemindingWorker(context:Context,params: WorkerParameters): Worker(context,params) {
    private val channelId = "reminder channel id"
    private var notificationManager: NotificationManager? = null

    override fun doWork(): Result {
        try {
            notificationManager = applicationContext
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(channelId,"reminder channel","Time to log desc")
            displaynotification()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(id, name, importance).apply { description = channelDescription }
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun displaynotification(){
        val notificationId = 35

        val tapResultIntent = Intent(applicationContext,MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(applicationContext,0,tapResultIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext,channelId)
            .setContentTitle("Time to log your vitals!")
            .setContentText("Stay on top of your health. Please update your vitals now!")
            .setSmallIcon(R.drawable.vitaltrace)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager?.notify(notificationId,notification)
    }
}