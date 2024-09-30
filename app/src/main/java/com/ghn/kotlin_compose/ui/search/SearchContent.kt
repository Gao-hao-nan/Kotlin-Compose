package com.ghn.kotlin_compose.ui.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ghn.kotlin_compose.base.EmojiData
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlin.random.Random

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiRain() {
    val context = LocalContext.current
    var emojis by remember { mutableStateOf(listOf<EmojiData>()) }
    val emojiList = remember { loadEmojis(context) }

    Box(modifier = Modifier
        .fillMaxSize()
        .combinedClickable(
            onClick = {
                val newEmoji = createRandomEmoji(emojiList)
                emojis = emojis + newEmoji // 添加新表情
            },
            onLongClick = {
                val newEmojis = emojis.toMutableList()
                addEmoji(newEmojis, emojiList, 5) // 添加多个表情
                emojis = newEmojis
            }
        )) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            emojis.forEach { emoji -> drawEmoji(emoji) }
        }

        emojis.forEach { emoji ->
            LaunchedEffect(emoji.id) {
                emoji.animY = Animatable(emoji.position.y)
                emoji.animY!!.animateTo(
                    targetValue = emoji.endPosition.y,
                    animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
                )
            }
        }
    }
}


private fun createRandomEmoji(emojiList: List<EmojiData>): EmojiData {
    return EmojiData(
        id = Random.nextInt(),
        emoji = emojiList.random().emoji,
        position = Offset(Random.nextFloat() * 400, 800f),
        endPosition = Offset(Random.nextFloat() * 400, 0f),
//        animY = null // 确保在创建时 animY 为 null
    )
}

private fun addEmoji(emojis: MutableList<EmojiData>, emojiList: List<EmojiData>, count: Int) {
    val randomEmojis = emojiList.shuffled().take(count)
    randomEmojis.forEach {
        emojis.add(createRandomEmoji(emojiList))
    }
}

// 其余代码保持不变




fun loadEmojis(context: Context): List<EmojiData> {
    val json = context.assets.open("emojis.json").bufferedReader().use { it.readText() }
    return Gson().fromJson(json, Array<EmojiData>::class.java).toList()
}

fun DrawScope.drawEmoji(emoji: EmojiData) {
    // 使用 Paint 设置文本样式
    val paint = android.graphics.Paint().apply {
        color = android.graphics.Color.BLACK // 颜色
        textSize = 60f // 字体大小，增大字体
    }
    // 计算文本的起始位置
    val x = emoji.position.x
    val y = emoji.animY?.value ?: emoji.position.y

    // 绘制文本
    drawContext.canvas.nativeCanvas.drawText(emoji.emoji, x, y, paint)
}

@Composable
@Preview(showBackground = true)
fun PreviewEmojiRain() {
    MaterialTheme {
        EmojiRain()
    }
}
