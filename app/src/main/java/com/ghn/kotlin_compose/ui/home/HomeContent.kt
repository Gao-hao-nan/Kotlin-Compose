package com.ghn.kotlin_compose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.ghn.kotlin_compose.R
import com.ghn.kotlin_compose.net.AriticleResponse
import com.ghn.kotlin_compose.ui.viewmodel.MainViewModel

/**
 * @author 浩楠
 *
 * @date 2024/9/20-13:37.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */

@Composable
fun ShowContent(viewModel: MainViewModel) {
    // 使用 collectAsState 获取 Flow 状态
    val uiState by viewModel.uiState.collectAsState()
    val articles = viewModel.articles.collectAsLazyPagingItems()

    // 只有一个 LazyColumn 来包含所有的内容
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 10.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(articles){ index, item->
            ListItem(item = item!!)
        }
        articles.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = (loadState.refresh as LoadState.Error).error
                    item { Text(text = "Error: $error") }
                }
            }
        }

        // 根据状态显示不同的 UI
//        when (uiState) {
//            is UiState.Idle -> {
//                // 初始状态，什么也不显示（或根据需求自定义UI）
//                item {
//                    Text(text = "点击按钮发起网络请求", color = Color.Gray)
//                }
//            }
//
//            is UiState.Loading -> {
//                // 显示加载中的界面
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .wrapContentSize(Alignment.Center)
//                    ) {
//                        CircularProgressIndicator() // 显示加载动画
//                    }
//                }
//            }
//
//            is UiState.Success<*> -> {
//                val data = (uiState as UiState.Success<*>).data
//                if (data is UiData.ArticleData) {
////                    // 显示 Banner 数据
////                    val bannerList = data.banners
////                    if (bannerList.isNotEmpty()) {
////                        item {
////                            HomeBanner().Carousel(banners = bannerList)
////                        }
////                    } else {
////                        item {
////                            Text(text = "没有 Banner 数据", color = Color.Gray)
////                        }
////                    }
//                    // 显示文章数据
//
//                    val articlesList =
//                        data.articles.datas ?: emptyList<AriticleResponse.DataX>()
//                    items(articlesList) { article ->
//                        ListItem(item = article)
//                    }
//                }
//            }
//
//            is UiState.Error -> {
//                // 显示错误信息
//                item {
//                    val message = (uiState as UiState.Error).message
//                    Text(text = "错误: $message", color = Color.Red)
//                }
//            }
//        }
    }
}

@Composable

fun ListItem(item: AriticleResponse.DataX) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .background(Color.White)
            .clickable { },
        shape = RoundedCornerShape(6.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            val (icon, author, tag, title, clock, date, star) = createRefs()

            Image(
                painter = painterResource(R.drawable.home_hot),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clip(CircleShape)
                    .size(14.dp)
            )

            ArticleText(
                text = item.getAuthorName(),
                modifier = Modifier
                    .constrainAs(author) {
                        start.linkTo(icon.end)
                        top.linkTo(icon.top)
                    }
                    .padding(start = 2.dp)
            )

            ArticleText(
                text = "${item.superChapterName}/${item.chapterName}",
                modifier = Modifier
                    .constrainAs(tag) {
                        start.linkTo(author.end)
                        baseline.linkTo(author.baseline)
                    }
                    .padding(start = 15.dp)
            )

            TitleText(
                text = item.title,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(icon.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(vertical = 5.dp)
            )

            Image(
                painter = painterResource(R.drawable.ic_time),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(clock) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                    }
                    .clip(CircleShape)
                    .size(14.dp)
            )

            ArticleText(
                text = item.niceDate,
                modifier = Modifier.constrainAs(date) {
                    start.linkTo(clock.end)
                    top.linkTo(clock.top)
                }
            )

            Image(
                painter = painterResource(if (item.collect) R.drawable.ic_launcher_background else R.drawable.timeline_like_normal),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(star) {
                        end.linkTo(parent.end)
                        top.linkTo(clock.top)
                        bottom.linkTo(clock.bottom)
                    }
                    .padding(start = 10.dp)
                    .clip(CircleShape)
                    .size(14.dp)
            )
        }
    }
}

@Composable
fun ArticleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = colorResource(R.color.color_8e9dac),
        modifier = modifier
    )
}

@Composable
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = colorResource(R.color.color_1c1c1e),
        modifier = modifier
    )
}

@Composable
@Preview
fun ArticleItemPreview() {
    val article = AriticleResponse.DataX(
        true, 1.toString(), 1, 1.toString(), false, 0, "", false,
        0, 10, false, 1.toString(), "2022-1-1 10:00", "date", 1.toString(), false.toString(),
        "", 1725984000000, 1, 0, 1725984000000, 1.toString(), 0, 1.toString(), "", 1, 0,
        1, 1
    )
    ListItem(article)
}




