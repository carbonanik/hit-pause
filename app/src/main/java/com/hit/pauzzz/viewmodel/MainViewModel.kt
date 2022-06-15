package com.hit.pauzzz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hit.pauzzz.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStoreManager
) : ViewModel() {

    val isServiceEnabled = dataStore.isServiceEnabled
    val isMessengerReplyEnabled = dataStore.isMessengerReplyEnabled
    val isWhatsappReplyEnabled = dataStore.isWhatsappReplyEnabled
    val isPhoneReplyEnabled = dataStore.isPhoneReplyEnabled


    var messengerT: String? = null
    var whatsappT: String? = null
    var phoneT: String? = null

    init {
        viewModelScope.launch {
            dataStore.messengerReplyText.filterNotNull()
                .collect { messengerT = it }
        }
        viewModelScope.launch {
            dataStore.whatsappReplyText.filterNotNull()
                .collect { whatsappT = it }
        }
        viewModelScope.launch {
            dataStore.phoneReplyText.filterNotNull()
                .collect { phoneT = it }
        }
    }


    fun saveMessengerAutoReply(text: String) {
        viewModelScope.launch { dataStore.setMessengerReplyText(text) }
    }

    fun saveWhatsAppAutoReply(text: String) {
        viewModelScope.launch { dataStore.setWhatsappReplyText(text) }
    }

    fun savePhoneAutoReply(text: String) {
        viewModelScope.launch { dataStore.setPhoneReplyText(text) }
    }

    fun setServiceEnabled(enabled: Boolean) {
        viewModelScope.launch { dataStore.setIsServiceEnabled(enabled) }
    }

}