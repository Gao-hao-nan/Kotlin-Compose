package com.ghn.kotlin_compose.net

import androidx.paging.PagingData

/**
 * @author 浩楠
 *
 * @date 2024/9/20-10:05.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 状态类
 */
sealed class UiState {
    data object Idle : UiState() // 初始状态
    data object Loading : UiState() // 加载状态
    data class Success<T>(val data: T) : UiState() // 成功状态，带数据
    data class Error(val message: String) : UiState() // 错误状态
}
sealed class UiData {
    data class BannerData(val banners: List<BannerBean>) : UiData()
    data class ArticleData(val articles: AriticleResponse) : UiData()
    data class CombinedData(
        val bannerData: BannerData,
        val articlePagingData: PagingData<AriticleResponse.DataX>
    ) : UiData()
}

