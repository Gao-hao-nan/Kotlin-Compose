package com.ghn.kotlin_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * @author 浩楠
 *
 * @date 2024/9/19-15:08.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */

/**
 * 在 [JetsnackApp] 中使用的导航目的地。
 */
object MainDestinations {
    const val HOME_ROUTE = "home" // 首页路由
    const val SNACK_DETAIL_ROUTE = "snack" // 小吃详情页路由
    const val SNACK_ID_KEY = "snackId" // 小吃ID的键
    const val ORIGIN = "origin" // 来源参数的键
}

/**
 * 记住并创建 [JetsnackNavController] 的实例。
 */
@Composable
fun rememberJetsnackNavController(
    navController: NavHostController = rememberNavController() // 默认使用 rememberNavController() 创建的导航控制器
): JetsnackNavController = remember(navController) {
    JetsnackNavController(navController) // 创建 JetsnackNavController 实例并记住
}

/**
 * 负责持有 UI 导航逻辑的类。
 */
@Stable
class JetsnackNavController(
    val navController: NavHostController, // Jetpack Navigation 的 NavHostController 实例
) {
    // ----------------------------------------------------------
    // 导航状态的真实来源
    // ----------------------------------------------------------
    fun upPress() {
        navController.navigateUp() // 执行导航向上操作（返回到上一个屏幕）
    }

    fun navigateToBottomBarRoute(route: String) {
        // 如果目标路由不是当前的路由，则进行导航
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop = true // 如果目标路由已经在栈顶，则重用
                restoreState = true // 恢复之前保存的状态
                // 弹出返回栈中的所有目标路由，并保存状态，这样可以在按返回键时回到开始的目标路由
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToSnackDetail(snackId: Long, origin: String, from: NavBackStackEntry) {
        // 为了避免重复的导航事件，我们检查生命周期状态
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.SNACK_DETAIL_ROUTE}/$snackId?origin=$origin")
        }
    }
}

/**
 * 如果生命周期没有恢复，则表示这个 NavBackStackEntry 已经处理了一个导航事件。
 * 这用于去重导航事件。
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId) // 获取图的起始目标

/**
 * 从 NavigationUI.kt 中复制的类似函数
 */
private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
