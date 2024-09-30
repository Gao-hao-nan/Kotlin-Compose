package com.ghn.kotlin_compose.net

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author 浩楠
 *
 * @date 2024/9/19-17:17.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
interface ApiService {
    @GET("banner/json")
    suspend fun getBanner(): BaseResultData<MutableList<BannerBean>>

    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResultData<AriticleResponse>
}