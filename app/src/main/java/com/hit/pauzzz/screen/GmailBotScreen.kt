package com.hit.pauzzz.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun GPrev() {
    GmailBotScreen {

    }
}

@Composable
fun GmailBotScreen(gmailAutoResponse: () -> Unit) {

    var subjectText by remember { mutableStateOf("") }
    var bodyText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Subject")
        TextField(value = "", onValueChange = {})
        Text(text = "Body")
        TextField(value = "", onValueChange = {})
        Button(onClick = { gmailAutoResponse() }) {
            Text(text = "Send")
        }
    }
}