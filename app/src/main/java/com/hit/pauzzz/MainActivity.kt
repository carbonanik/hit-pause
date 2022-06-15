package com.hit.pauzzz

import android.Manifest.*
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hit.pauzzz.screen.*
import com.hit.pauzzz.service.AutoRespondService
import com.hit.pauzzz.ui.theme.HitPauzzzTheme
import com.hit.pauzzz.util.myRequestPermission
import com.hit.pauzzz.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast

import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager

import androidx.core.app.ActivityCompat
import com.hit.pauzzz.service.MyNotificationManager
import com.hit.pauzzz.service.NotificationData
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var enableNotificationListenerAlertDialog: AlertDialog? = null
    private var myNotificationManager = MyNotificationManager(this)

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            myNotificationManager.createFloatingNotificationChannel()
        }
        myNotificationManager.sendNotification(NotificationData(
            "Hitzzz", "Hi How Are You?"
        ), 101)

        setContent {
            HitPauzzzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    Column(Modifier.fillMaxSize()) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(Color.Green)
                        ) {
                            Text(text = "Demo App <3")
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route
                        ) {

                            composable(route = Screen.LogInScreen.route) {
                                LoginScreen(navController = navController) {}
                            }
                            composable(route = Screen.HomeScreen.route) {
                                HomeScreen(navController = navController, viewModel = viewModel)
                            }
                            composable(Screen.GmailBotScreen.route) {
                                GmailBotScreen {}
                            }
                            composable(Screen.FacebookBotScreen.route) {
                                FacebookBotScreen(viewModel)
                            }
                            composable(Screen.WhatsAppBotScreen.route) {
                                WhatsAppBotScreen(viewModel)
                            }
                            composable(Screen.AutoReplyPhoneMessage.route) {
                                PhoneMessageBotScreen(viewModel)
                            }

                        }
                    }
                }
            }
        }

        myRequestPermission(
            permission.RECEIVE_SMS,
            permission.SEND_SMS
        ) {}

        sendSMS(this, "+8801766785027", "hi sending message :)")

        if (!isNotificationServiceEnabled) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog!!.show()
        }

        intent = Intent(this, AutoRespondService::class.java)
        if (!isServiceRunning(AutoRespondService::class.java)) {
            startService(intent)
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name.equals(service.service.className)) return true
        }
        return false
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


    private fun sendSMS(context: Context?, phoneNo: String?, msg: String?) {
        context ?: return
        if (phoneNo.isNullOrEmpty() || msg.isNullOrEmpty()) return
        if (ActivityCompat.checkSelfPermission(context, permission.SEND_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val smsManager = SmsManager.getDefault()
//                    ContextCompat.getSystemService(context, SmsManager::class.java)
                if (msg.length <= 160) {
                    smsManager?.sendTextMessage(phoneNo, null, msg, null, null)
                    Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
                } else {
                    val parts = smsManager?.divideMessage(msg)
                    smsManager?.sendMultipartTextMessage(phoneNo, null, parts, null, null)
                    Toast.makeText(this, "Multi Message sent", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}
