package com.hit.pauzzz.screen

import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hit.pauzzz.data.UserData.DEFAULT_AUTO_REPLY_TEXT
import com.hit.pauzzz.ui.theme.HitPauzzzTheme
import com.hit.pauzzz.viewmodel.MainViewModel

@Preview
@Composable
fun WABSPrev() {
    HitPauzzzTheme {
        Surface {
            WhatsAppBotScreen(viewModel())
        }
    }
}

@Composable
fun WhatsAppBotScreen(viewModel: MainViewModel) {
    val title = "WhatsApp Auto Respond"
    val items by remember {
        mutableStateOf(
            listOf("Select Status", "Available", "Closed", "Busy")
        )
    }

    BotTextScreen(
        title = title,
        items = items,
        selectedItem = items[0],
        savedAutoReplyText = viewModel.whatsappT ?: DEFAULT_AUTO_REPLY_TEXT,
        saveAutoReplyText = { autoReplyText ->
            viewModel.saveWhatsAppAutoReply(autoReplyText)
        }
    )
}