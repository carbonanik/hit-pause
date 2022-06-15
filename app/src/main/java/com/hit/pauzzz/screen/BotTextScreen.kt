package com.hit.pauzzz.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.pauzzz.screen.composeview.ComposeMenu

@Composable
fun BotTextScreen(
    title: String,
    items: List<String>,
    selectedItem: String,
    savedAutoReplyText: String,
    saveAutoReplyText: (text: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        var itemsExpanded by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(items.indexOf(selectedItem)) }
        var autoReplyText by remember { mutableStateOf(savedAutoReplyText) }
        var saveButtonText by remember { mutableStateOf("Save") }

        Text(
            title,//"Facebook Auto Respond",
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


        TextField(
            value = autoReplyText,
            onValueChange = {
                autoReplyText = it
                saveButtonText = "Save"
            }, Modifier.fillMaxWidth()
        )
        println("$autoReplyText-$title")
        Spacer(Modifier.height(30.dp))

        Button(
            enabled = saveButtonText == "Save",
            onClick = {
                saveButtonText = "Saved"
                saveAutoReplyText(autoReplyText)
            },
            shape = RoundedCornerShape(size = 22.5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
        ) {
            Text(text = saveButtonText, fontSize = 16.sp)
        }
    }
}