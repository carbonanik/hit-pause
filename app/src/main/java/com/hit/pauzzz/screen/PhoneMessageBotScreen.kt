package com.hit.pauzzz.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hit.pauzzz.R
import com.hit.pauzzz.data.UserData
import com.hit.pauzzz.viewmodel.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PhoneMessagePreview() {
    Surface(color = Color.White) {
        PhoneMessageBotScreen(viewModel())
    }
}

@ExperimentalFoundationApi
@Composable
fun PhoneMessageBotScreen(viewModel: MainViewModel) {
    val title = "Phone Message Auto Respond"
    val items by remember {
        mutableStateOf(
            listOf("Select Status", "Available", "Closed", "Busy")
        )
    }

    BotTextScreen(
        title = title,
        items = items,
        selectedItem = items[0],
        savedAutoReplyText = viewModel.phoneT ?: UserData.DEFAULT_AUTO_REPLY_TEXT,
        saveAutoReplyText = { autoReplyText ->
            viewModel.savePhoneAutoReply(autoReplyText)
        }
    )
}