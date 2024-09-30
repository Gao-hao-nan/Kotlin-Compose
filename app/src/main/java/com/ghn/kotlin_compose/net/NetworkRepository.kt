package com.ghn.kotlin_compose.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

/**
 * @author 浩楠
 *
 * @date 2024/9/19-17:18.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
class NetworkRepository(private val apiService: ApiService) {
    fun getBanner(): Flow<Result<BaseResultData<MutableList<BannerBean>>>> = flow {
        val response = apiService.getBanner()
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }

    fun getArticleList(page: Int): Flow<Result<BaseResultData<AriticleResponse>>> = flow {
            val response = apiService.getArticleList(page)
            emit(Result.success(response))
        }.catch { e ->
            emit(Result.failure(e))
        }

    fun createArticlePagingSource(): PagingSource<Int, AriticleResponse.DataX> {
        return object : PagingSource<Int, AriticleResponse.DataX>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AriticleResponse.DataX> {
                val page = params.key ?: 1 // 从参数获取当前页码
                return try {
                    val response = apiService.getArticleList(page) // 调用 API 获取数据
                    val articles = response.data.datas // 提取文章数据

                    LoadResult.Page(
                        data = articles, // 当前页的数据
                        prevKey = if (page == 1) null else page - 1, // 上一页
                        nextKey = if (articles.isEmpty()) null else page + 1 // 下一页
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e) // 捕获异常
                }
            }

            override fun getRefreshKey(state: PagingState<Int, AriticleResponse.DataX>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val closestPageToAnchor = state.closestPageToPosition(anchorPosition)
                    closestPageToAnchor?.prevKey?.plus(1) ?: closestPageToAnchor?.nextKey?.minus(1)
                }
            }
        }
    }
}