package com.hit.pauzzz.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.hit.pauzzz.MainActivity
import com.hit.pauzzz.R

class MyNotificationManager(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createFloatingNotificationChannel() {
        createChannel(FLOATING_CHANNEL_ID, FLOATING_CHANNEL_NAME)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(id: String, name: String) {
        val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_NONE)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val manager = context.getNotificationManager()
        manager?.createNotificationChannel(channel)
    }

    fun sendNotification(notificationData: NotificationData, notificationId: Int) {
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notificationBuilder(notificationData).build())
        }
    }

    private fun notificationBuilder(notificationData: NotificationData) =
        NotificationCompat.Builder(context, FLOATING_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(notificationData.title)
            setContentText(notificationData.text)
//            setContentIntent()
//            setAutoCancel(true)
        }

    private fun buildPendingIntent(): PendingIntent? {
        val openChatIntent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(openChatIntent)
            getPendingIntent(
                REQUEST_CODE_OPEN_CHAT,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    companion object {
        const val FLOATING_CHANNEL_ID = "floating_notification_channel"
        const val FLOATING_CHANNEL_NAME = "Floating Notification Channel"

        const val REQUEST_CODE_OPEN_CHAT = 1_121_111
    }
}

fun Context.getNotificationManager() =
    getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager


data class NotificationData(
    val title: String,
    val text: String
)