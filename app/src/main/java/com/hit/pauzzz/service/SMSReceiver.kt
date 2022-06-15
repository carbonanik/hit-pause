package com.hit.pauzzz.service

import android.Manifest.permission.SEND_SMS
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.hit.pauzzz.data.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SMSReceiver : BroadcastReceiver() {

    @Inject
    lateinit var dataStore: DataStoreManager

    override fun onReceive(context: Context?, intent: Intent) {
        val scope = CoroutineScope(Dispatchers.Default)
        val prEnable = scope.async {
            dataStore.isPhoneReplyEnabled.filterNotNull().first()
        }
        val prText = scope.async {
            dataStore.phoneReplyText.filterNotNull().first()
        }

        scope.launch {
            if (prEnable.await()){
//                receiveSms(intent, context) { phone ->
//                    sendSMS(context, phone, prText.await())
//                }
            }
        }

    }

    private suspend fun receiveSms(intent: Intent, context: Context?, sendSms: suspend (phoneNo: String) -> Unit){
        if (SMS_RECEIVED_ACTION == intent.action) {
            for (smsMessage in getMessagesFromIntent(intent)) {
                val sender = smsMessage.originatingAddress ?: return
                val messageBody = smsMessage.messageBody

                sendSms(sender)
//                sendSMS(context, sender, "hi sending message :)")
            }
        }
    }

    private fun sendSMS(context: Context?, phoneNo: String?, msg: String?) {
        context ?: return
        if (phoneNo.isNullOrEmpty() || msg.isNullOrEmpty()) return
        if (ActivityCompat.checkSelfPermission(context, SEND_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val smsManager =
                    getSystemService(context, SmsManager::class.java)
                if (msg.length <= 160) {
//                    smsManager?.sendTextMessage(phoneNo, null, msg, null, null)
                    println("Message Sent")
                } else {
                    val parts = smsManager?.divideMessage(msg)
//                    smsManager?.sendMultipartTextMessage(phoneNo, null, parts, null, null)
                    println("Multipart Message Sent")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}