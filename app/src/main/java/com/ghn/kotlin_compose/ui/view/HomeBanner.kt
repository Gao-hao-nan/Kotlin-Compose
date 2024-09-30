package com.ghn.kotlin_compose.ui.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.ghn.kotlin_compose.net.BannerBean
import com.ghn.kotlin_compose.ui.activity.WebviewActivity
import kotlin.math.absoluteValue
import kotlinx.coroutines.delay

/**
 * @author 浩楠
 *
 * @date 2024/9/20-14:36.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
class HomeBanner {
    @Composable
    fun Carousel(banners: List<BannerBean>) {
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { banners.size })
        val context = LocalContext.current // 获取当前上下文
        HorizontalPager(state = pagerState) { page ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        context.startActivity(Intent(context,WebviewActivity::class.java).apply {
                            putExtra("url",banners[page].url)
                        })
                    }
                    .graphicsLayer {
                        // 计算当前页相对于滚动位置
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                ).absoluteValue

                        // 动画 alpha
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        // 翻转效果
                        rotationY = if (pageOffset < 1f) {
                            (pageOffset * 180).coerceIn(0f, 180f)
                        } else {
                            180f
                        }
                    }

            ){
                Image(
                    painter = rememberImagePainter(banners[page].imagePath),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        LaunchedEffect(Unit) {
            while (true) {
                delay(2000) // 每3秒切换一次
                val nextPage = (pagerState.currentPage + 1) % banners.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

}