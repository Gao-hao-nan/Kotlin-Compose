package com.ghn.kotlin_compose.base

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.Offset

/**
 * @author 浩楠
 *
 * @date 2024/9/24-14:34.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
data class EmojiData(
    val id: Int,
    val emoji: String,
    val description: String? = null, // 可选字段
    var position: Offset,
    val endPosition: Offset,
    var animY: Animatable<Float, AnimationVector1D>? = null,
    var isAnimating: Boolean = false // 新增状态
)
