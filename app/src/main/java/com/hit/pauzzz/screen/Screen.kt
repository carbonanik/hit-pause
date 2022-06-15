package com.hit.pauzzz.screen

sealed class Screen(val route: String){
    object LogInScreen: Screen("login")
    object HomeScreen: Screen("home")
    object GmailBotScreen: Screen("gmail_bot")
    object FacebookBotScreen: Screen("facebook_bot"){
        val deepLink = ""
    }
    object AutoReplyPhoneMessage: Screen("phone_message")
    object WhatsAppBotScreen: Screen("whatsapp_bot")
//    object BotTextScreen: Screen("bot_text_screen"){
//        fun appName(name: String): String {
//            return route + name
//        }
//    }
}
