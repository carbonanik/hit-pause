package com.hit.pauzzz.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hit.pauzzz.R
import com.hit.pauzzz.screen.composeview.SocialAppButton
import com.hit.pauzzz.viewmodel.MainViewModel
import kotlinx.coroutines.flow.filterNotNull

@Preview
@Composable
fun HomePreview() {
//    HomeScreen()
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Spacer(modifier = Modifier.height(40.dp))

        /**
         *  LOGO AND TITLE
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(30.dp))
            IconButton(
                onClick = {},
                Modifier
                    .size(45.dp)
                    .background(Color.Green, RoundedCornerShape(25.dp))
            ) { Text(text = "logo", fontWeight = FontWeight.Bold, color = Color.White) }
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                "Which App Will You Pause?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        LazyVerticalGrid(modifier = Modifier.fillMaxWidth(), cells = GridCells.Fixed(3), content = {
            items(homeGridList) { gridItem ->
                SocialAppButton(
                    drawableId = gridItem.drawableId,
                    name = gridItem.name,
                    modifier = Modifier.padding(4.dp)
                ) {
                    gridItem.route?.let { navController.navigate(it) }
                }
            }
        })

        Spacer(modifier = Modifier.height(25.dp))

        val isEnabled by viewModel.isServiceEnabled.filterNotNull().collectAsState(initial = false)

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { viewModel.setServiceEnabled(!isEnabled) },
                Modifier
                    .size(75.dp)
                    .background(Color.Green, RoundedCornerShape(25.dp))
            ) {
                Text(
                    text = if (isEnabled) "Stop" else "Start",
                    fontWeight = FontWeight.Bold, color = Color.White
                )
            }
        }

//
//        Spacer(modifier = Modifier.height(50.dp))
//
//        var opinionText by remember { mutableStateOf("") }
//
//        Text(text = "What Other App Not Listed Would You Like To Pause?", fontSize = 20.sp)
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//
//        TextField(
//            value = opinionText,
//            onValueChange = { opinionText = it },
//            modifier = Modifier.fillMaxWidth(),
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//
//        Button(
//            onClick = { },
//            shape = RoundedCornerShape(size = 22.5.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(45.dp),
//        ) {
//            Text(
//                text = "Submit",
//                fontSize = 16.sp
//            )
//        }
//
//        Spacer(modifier = Modifier.height(40.dp))
    }
}


val homeGridList = listOf(
    HomeGridItem(
        "Message",
        R.drawable.messages_icon_256,
        Screen.AutoReplyPhoneMessage.route
    ),
    HomeGridItem(
        "Facebook",
        R.drawable.facebook_256x256,
        Screen.FacebookBotScreen.route
    ),
    HomeGridItem(
        "WhatsApp",
        R.drawable.whatsapp_256x256,
        Screen.WhatsAppBotScreen.route
    ),
    HomeGridItem(
        "LinkedIn",
        R.drawable.linkedin_256x256
    ),
    HomeGridItem(
        "Slack",
        R.drawable.slack_256x256
    ),
    HomeGridItem(
        "Telegram",
        R.drawable.telegram_256x256
    )

)

data class HomeGridItem(
    val name: String,
    val drawableId: Int,
    val route: String? = null
)