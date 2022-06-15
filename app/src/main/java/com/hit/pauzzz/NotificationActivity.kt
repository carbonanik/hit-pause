package com.hit.pauzzz

import android.content.*
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hit.pauzzz.service.AutoRespondService.Companion.FACEBOOK_CODE
import com.hit.pauzzz.service.AutoRespondService.Companion.INSTAGRAM_CODE
import com.hit.pauzzz.service.AutoRespondService.Companion.WHATSAPP_CODE
import com.hit.pauzzz.ui.theme.HitPauzzzTheme


class NotificationActivity : AppCompatActivity() {
    private var notificationBroadcastReceiver: NotificationBroadcastReceiver? = null
    private var enableNotificationListenerAlertDialog: AlertDialog? = null
    var incoming by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            HitPauzzzTheme {
                Surface {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Notification: $incoming")
                    }
                }
            }
        }

        if (!isNotificationServiceEnabled) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog!!.show()
        }

        notificationBroadcastReceiver = NotificationBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.hit.pauzzz")
        registerReceiver(notificationBroadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(notificationBroadcastReceiver)
    }

    private fun changeNotificationApp(notificationCode: Int) {
        incoming = when (notificationCode) {
            FACEBOOK_CODE -> "Facebook Messenger"
            INSTAGRAM_CODE -> "Instagram"
            WHATSAPP_CODE -> "WhatsApp"
            else -> "Not Supported"
        }
    }

    private val isNotificationServiceEnabled: Boolean
        get() {
            val pkgName = packageName
            val flat = Settings.Secure.getString(
                contentResolver,
                ENABLED_NOTIFICATION_LISTENERS
            )

            if (!flat.isNullOrEmpty()) {
                val names = flat.split(":").toTypedArray()
                for (i in names.indices) {
                    val cn = ComponentName.unflattenFromString(names[i])
                    if (cn != null) {
                        if (pkgName.equals(cn.packageName)) {
                            return true
                        }
                    }
                }
            }
            return false
        }


    inner class NotificationBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val receivedNotificationCode = intent.getIntExtra("Notification Code", -1)
            changeNotificationApp(receivedNotificationCode)
        }
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(R.string.notification_listener_service)
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation)
        alertDialogBuilder.setPositiveButton("Yes")
        { dialog, id ->
            startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        alertDialogBuilder.setNegativeButton("No")
        { dialog, id -> }
        return alertDialogBuilder.create()
    }

    companion object {
        private const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        private const val ACTION_NOTIFICATION_LISTENER_SETTINGS =
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    }
}