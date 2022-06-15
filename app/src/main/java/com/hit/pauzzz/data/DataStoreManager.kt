package com.hit.pauzzz.data

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.hit.pauzzz.data.UserData.DEFAULT_AUTO_REPLY_TEXT
import com.hit.pauzzz.data.UserData.MESSENGER_REPLY
import com.hit.pauzzz.data.UserData.MESSENGER_REPLY_ENABLED
import com.hit.pauzzz.data.UserData.PHONE_REPLY
import com.hit.pauzzz.data.UserData.PHONE_REPLY_ENABLED
import com.hit.pauzzz.data.UserData.SERVICE_ENABLED
import com.hit.pauzzz.data.UserData.USER_DATA_PREFERENCES
import com.hit.pauzzz.data.UserData.WHATSAPP_REPLY
import com.hit.pauzzz.data.UserData.WHATSAPP_REPLY_ENABLED
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(USER_DATA_PREFERENCES)

class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
    private val dataStore = appContext.dataStore

    val isServiceEnabled: Flow<Boolean?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[SERVICE_ENABLED] ?: false
        }

    suspend fun setIsServiceEnabled(enabled: Boolean){
        dataStore.edit { preference ->
            preference[SERVICE_ENABLED] = enabled
        }
    }

    /**
     * Messenger
     */
    val isMessengerReplyEnabled: Flow<Boolean?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[MESSENGER_REPLY_ENABLED] ?: true
        }

    suspend fun setIsMessengerReplyEnabled(enabled: Boolean){
        dataStore.edit { preference ->
            preference[MESSENGER_REPLY_ENABLED] = enabled
        }
    }

    val messengerReplyText: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[MESSENGER_REPLY] ?: DEFAULT_AUTO_REPLY_TEXT
        }

    suspend fun setMessengerReplyText(text: String) {
        dataStore.edit { preference ->
            preference[MESSENGER_REPLY] = text
        }
    }

    /**
     * WhatsApp
     */

    val isWhatsappReplyEnabled: Flow<Boolean?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[WHATSAPP_REPLY_ENABLED] ?: true
        }

    suspend fun setIsWhatsappReplyEnabled(enabled: Boolean){
        dataStore.edit { preference ->
            preference[WHATSAPP_REPLY_ENABLED] = enabled
        }
    }

    val whatsappReplyText: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[WHATSAPP_REPLY] ?: DEFAULT_AUTO_REPLY_TEXT
        }

    suspend fun setWhatsappReplyText(text: String){
        dataStore.edit { preference ->
            preference[WHATSAPP_REPLY] = text
        }
    }

    /**
     * Phone
     */
    val isPhoneReplyEnabled: Flow<Boolean?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[PHONE_REPLY_ENABLED] ?: true
        }

    suspend fun setIsPhoneReplyEnabled(enabled: Boolean){
        dataStore.edit { preference ->
            preference[PHONE_REPLY_ENABLED] = enabled
        }
    }

    val phoneReplyText: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[PHONE_REPLY] ?: DEFAULT_AUTO_REPLY_TEXT
        }

    suspend fun setPhoneReplyText(text: String){
        dataStore.edit { preference ->
            preference[PHONE_REPLY] = text
        }
    }
}

object UserData {

    const val USER_DATA_PREFERENCES = "user_data"

    val SERVICE_ENABLED = booleanPreferencesKey("service_enabled")

    val MESSENGER_REPLY_ENABLED = booleanPreferencesKey("messenger_reply_enabled")
    val MESSENGER_REPLY = stringPreferencesKey(name = "messenger_reply")

    val WHATSAPP_REPLY_ENABLED = booleanPreferencesKey("whatsapp_reply_enabled")
    val WHATSAPP_REPLY = stringPreferencesKey(name = "whatsapp_reply")

    val PHONE_REPLY_ENABLED = booleanPreferencesKey("phone_reply_enabled")
    val PHONE_REPLY = stringPreferencesKey("phone_reply")


    const val DEFAULT_AUTO_REPLY_TEXT = "Auto Reply From Hit Pause :)"

}