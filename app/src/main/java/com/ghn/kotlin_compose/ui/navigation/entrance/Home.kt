package com.ghn.kotlin_compose.ui.navigation.entrance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghn.kotlin_compose.ui.viewmodel.MainViewModel
import androidx.compose.runtime.*
import com.ghn.kotlin_compose.ui.home.ShowContent


/**
 * @author 浩楠
 *
 * @date 2024/9/19-15:42.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    viewModel.loadCombinedData()
    ShowContent(viewModel)
}

@Composable
fun SearchScreen() {
//    KickingScreen()
}

@Composable
fun NotificationsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Text(text = "Notifications Screen", color = Color.Black)
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Text(text = "Profile Screen", color = Color.White)
    }
}

