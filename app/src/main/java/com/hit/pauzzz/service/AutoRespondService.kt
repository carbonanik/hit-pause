package com.hit.pauzzz.service

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast
import androidx.core.app.RemoteInput
import com.hit.pauzzz.data.DataStoreManager
import com.hit.pauzzz.data.UserData
import com.hit.pauzzz.data.UserData.DEFAULT_AUTO_REPLY_TEXT
import com.hit.pauzzz.util.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AutoRespondService : NotificationListenerService() {

    @Inject
    lateinit var dataStore: DataStoreManager

    companion object {
        //const val FACEBOOK_PACK_NAME = "com.facebook.katana"
        const val FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca"
        const val WHATSAPP_PACK_NAME = "com.whatsapp"
        const val INSTAGRAM_PACK_NAME = "com.instagram.android"

        const val FACEBOOK_CODE = 1
        const val WHATSAPP_CODE = 2
        const val INSTAGRAM_CODE = 3
    }

    private var isServiceEnabled: Boolean = false
    private var isMessengerReplyEnabled = true
    private var isWhatsappReplyEnabled = true
    private var messengerReplyText = DEFAULT_AUTO_REPLY_TEXT
    private var whatsAppReplyText = DEFAULT_AUTO_REPLY_TEXT

    override fun onCreate() {
        super.onCreate()
        println("OnCreate Service")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            dataStore.isServiceEnabled.filterNotNull()
                .collect { isServiceEnabled = it; println(it) }
        }
        scope.launch {

            dataStore.isMessengerReplyEnabled.filterNotNull()
                .collect { isMessengerReplyEnabled = it; println(it) }
        }
        scope.launch {
            dataStore.isWhatsappReplyEnabled.filterNotNull()
                .collect { isWhatsappReplyEnabled = it; println(it) }
        }
        scope.launch {

            dataStore.messengerReplyText.filterNotNull()
                .collect { messengerReplyText = it; println(it) }
        }
        scope.launch {
            dataStore.whatsappReplyText.filterNotNull()
                .collect { whatsAppReplyText = it; println(it) }
        }
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        sendReplyForApp(sbn)
    }

    private fun sendReply(sbn: StatusBarNotification, replyText: String) {

        val (_, _, remoteInputs1) = NotificationUtils.extractWearNotification(sbn)
        if (remoteInputs1.isEmpty()) {
            return
        }
        val remoteInputs = arrayOfNulls<RemoteInput>(remoteInputs1.size)
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val localBundle = Bundle() //notificationWear.bundle;

        remoteInputs1.withIndex().forEach { (i, remoteIn) ->
            remoteInputs[i] = remoteIn
            localBundle.putCharSequence(
                remoteInputs[i]!!.resultKey,
                replyText
            )
        }
        RemoteInput.addResultsToIntent(remoteInputs, localIntent, localBundle)
        try {
            println("Sending Auto Reply...")
            Toast.makeText(applicationContext, replyText, Toast.LENGTH_SHORT).show()
//            pendingIntent?.send(this, 0, localIntent)
        } catch (e: PendingIntent.CanceledException) {
            Log.e("NotificationListener", "replyToLastNotification error: " + e.localizedMessage)
        }
    }

    private fun sendReplyForApp(sbn: StatusBarNotification) {
        when (sbn.packageName) {
            FACEBOOK_MESSENGER_PACK_NAME -> {
                if (isServiceEnabled && isMessengerReplyEnabled) {
                    sendReply(sbn, messengerReplyText)
                }
            }
            WHATSAPP_PACK_NAME -> {
                if (isServiceEnabled && isWhatsappReplyEnabled) {
                    sendReply(sbn, whatsAppReplyText)
                }
            }
            INSTAGRAM_PACK_NAME -> {

            }
        }
    }
}