package com.ghn.kotlin_compose.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.ghn.kotlin_compose.R
import kotlin.reflect.KClass

/**
 * @author 浩楠
 *
 * @date 2024/9/18-14:00.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
//    FOR_YOU(
//        selectedIcon = NiaIcons.Upcoming,
//        unselectedIcon = NiaIcons.UpcomingBorder,
//        iconTextId = forYouR.string.feature_foryou_title,
//        titleTextId = R.string.app_name,
//        route = ForYouRoute::class,
//    ),
//    BOOKMARKS(
//        selectedIcon = NiaIcons.Bookmarks,
//        unselectedIcon = NiaIcons.BookmarksBorder,
//        iconTextId = bookmarksR.string.feature_bookmarks_title,
//        titleTextId = bookmarksR.string.feature_bookmarks_title,
//        route = BookmarksRoute::class,
//    ),
//    INTERESTS(
//        selectedIcon = NiaIcons.Grid3x3,
//        unselectedIcon = NiaIcons.Grid3x3,
//        iconTextId = searchR.string.feature_search_interests,
//        titleTextId = searchR.string.feature_search_interests,
//        route = InterestsRoute::class,
//    ),
}