package com.hit.pauzzz.data

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
//
//object DataStoreRepository {
//
//    private var dataStore: DataStoreManager? = null
//    var isServiceEnabled = false
//    var isMessengerReplyEnabled = true
//    var isWhatsappReplyEnabled = true
//    var messengerReplyText = "Auto Reply From Hit Pause :)"
//    var whatsAppReplyText = "Auto Reply From Hit Pause :)"
//
//    val isServiceEnabledFlow = dataStore?.isServiceEnabled
//
//    private var job: Job? = null
//
//
//    fun create(context: Application): DataStoreRepository {
//        if (dataStore == null){
//            dataStore = DataStoreManager.getInstance(context)
//        }
//
//        if (job == null){
//            job = CoroutineScope(Dispatchers.IO).launch {
//                dataStore?.isServiceEnabled?.filterNotNull()
//                    ?.collect {
//                        isServiceEnabled = it
//                        println(it)
//                    }
//
//                dataStore?.isMessengerReplyEnabled?.filterNotNull()
//                    ?.collect { isMessengerReplyEnabled = it }
//                dataStore?.isWhatsappReplyEnabled?.filterNotNull()
//                    ?.collect { isWhatsappReplyEnabled = it }
//
//                dataStore?.messengerReplyText?.filterNotNull()
//                    ?.collect { messengerReplyText = it }
//                dataStore?.whatsappReplyText?.filterNotNull()
//                    ?.collect { whatsAppReplyText = it }
//            }
//        }
//        return this
//    }
//
//
//    suspend fun setServiceEnabled(enabled: Boolean) = dataStore?.setIsServiceEnabled(enabled)
//    suspend fun setMessengerReplyEnabled(enabled: Boolean) = dataStore?.setIsMessengerReplyEnabled(enabled)
//    suspend fun setWhatsappReplyEnabled(enabled: Boolean) = dataStore?.setIsWhatsappReplyEnabled(enabled)
//
//    suspend fun saveMessengerAutoReply(text: String) = dataStore?.setMessengerReplyText(text)
//    suspend fun saveWhatsAppAutoReply(text: String)  =       dataStore?.setWhatsappReplyText(text)
//}