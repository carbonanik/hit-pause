package com.hit.pauzzz.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hit.pauzzz.data.UserData.DEFAULT_AUTO_REPLY_TEXT
import com.hit.pauzzz.screen.composeview.ComposeMenu
import com.hit.pauzzz.ui.theme.HitPauzzzTheme
import com.hit.pauzzz.viewmodel.MainViewModel

@Preview
@Composable
fun FBSPrev() {
    HitPauzzzTheme {
        Surface {
            FacebookBotScreen(viewModel())
        }
    }
}

@Composable
fun FacebookBotScreen(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val items by remember {
            mutableStateOf(
                listOf("Select Status", "Available", "Closed", "Busy")
            )
        }
        var itemsExpanded by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(0) }

        var autoReplyText by remember { mutableStateOf(viewModel.messengerT ?: DEFAULT_AUTO_REPLY_TEXT) }

        Text(
            "Facebook Auto Respond",
            Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(30.dp))
        Text("Status", Modifier.padding(bottom = 8.dp), fontSize = 20.sp)
        ComposeMenu(
            menuItems = items,
            menuExpandedState = itemsExpanded,
            seletedIndex = selectedIndex,
            updateMenuExpandStatus = {
                itemsExpanded = true
            },
            onDismissMenuView = {
                itemsExpanded = false
            },
            onMenuItemclick = { index ->
                selectedIndex = index
                itemsExpanded = false
            }
        )

        Spacer(Modifier.height(20.dp))
        Text(text = "Auto Reply", Modifier.padding(bottom = 8.dp), fontSize = 20.sp)

        var save by remember { mutableStateOf("Save") }

        TextField(value = autoReplyText,
            onValueChange = {
                autoReplyText = it
                save = "Save"
            }, Modifier.fillMaxWidth()
        )
        println(autoReplyText+"-Screen")
        Spacer(Modifier.height(30.dp))

        Button(
            enabled = save == "Save",
            onClick = {
                save = "Saved"
                viewModel.saveMessengerAutoReply(
                    autoReplyText
                )
            },
            shape = RoundedCornerShape(size = 22.5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
        ) {
            Text(text = save, fontSize = 16.sp)
        }
    }
}