package com.ghn.kotlin_compose.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ghn.kotlin_compose.net.AriticleResponse
import com.ghn.kotlin_compose.ui.navigation.entrance.HomeScreen
import com.ghn.kotlin_compose.ui.navigation.entrance.NotificationsScreen
import com.ghn.kotlin_compose.ui.navigation.entrance.ProfileScreen
import com.ghn.kotlin_compose.ui.navigation.entrance.SearchScreen
import com.ghn.kotlin_compose.ui.theme.Neutral5
import com.ghn.kotlin_compose.ui.theme.Neutral8
import com.google.accompanist.navigation.animation.AnimatedNavHost

/**
 * @author 浩楠
 *
 * @date 2024/9/18-13:55.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO ComPose navigation 底部导航
 */

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavScreen.entries.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.name,
                onClick = {
                    navController.navigate(screen.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Neutral8,
                    unselectedIconColor = Neutral5,
                    selectedTextColor = Neutral8,
                    unselectedTextColor = Neutral5
                )
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = BottomNavScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = BottomNavScreen.Home.name,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
            ) {
                HomeScreen()
            }
            composable(
                route = BottomNavScreen.Search.name,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
            ) {
                SearchScreen()
            }
            composable(
                route = BottomNavScreen.Notifications.name,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
            ) {
                NotificationsScreen()
            }
            composable(
                route = BottomNavScreen.Profile.name,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
            ) {
                ProfileScreen()
            }
            // 添加 WebView 路由
//            composable(
//                route = "web/{url}",
//                arguments = listOf(navArgument("url") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val url = backStackEntry.arguments?.getString("url") ?: ""
//                WebViewScreen(url)
//            }


        }
    }
}