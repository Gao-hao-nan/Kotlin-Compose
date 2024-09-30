package com.ghn.kotlin_compose.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ghn.kotlin_compose.base.BaseViewModel
import com.ghn.kotlin_compose.net.AriticleResponse
import com.ghn.kotlin_compose.net.NetworkRepository
import com.ghn.kotlin_compose.net.RetrofitClient
import com.ghn.kotlin_compose.net.UiData
import com.ghn.kotlin_compose.net.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * @author 浩楠
 *
 * @date 2024/9/19-17:12.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
class MainViewModel : BaseViewModel() {
    private val apiService = RetrofitClient().provideApiService(RetrofitClient().provideRetrofit())
    private val networkRepository = NetworkRepository(apiService)
    // 处理网络请求中的异常
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.value = UiState.Error(exception.message ?: "Unknown error")
    }
    // Flow 代表 UI 状态，初始值是 Idle
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    // 加载 Banner 数据
    fun getBanner() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(exceptionHandler) {
            networkRepository.getBanner()
                .collect { result ->  // 收集 Flow 中的结果
                    result.onSuccess { response ->
                        // 成功获取数据时，更新 UI 状态
                        _uiState.value = UiState.Success(UiData.BannerData(response.data))
                    }
                    result.onFailure { error ->
                        // 处理失败情况，更新 UI 状态为错误信息
                        _uiState.value = UiState.Error(error.message ?: "Unknown error")
                    }
                }
        }
    }

    val articles: Flow<PagingData<AriticleResponse.DataX>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { networkRepository.createArticlePagingSource()}
    ).flow.cachedIn(viewModelScope)

    // 加载 Banner 数据和文章数据
    fun loadCombinedData() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(exceptionHandler) {
            val bannerFlow = networkRepository.getBanner()
            val articlesFlow = Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { networkRepository.createArticlePagingSource() }
            ).flow

            // 使用 combine 来合并两个流
            combine(bannerFlow, articlesFlow) { bannerResult, articlePagingData ->
                bannerResult.onSuccess { bannerResponse ->
                    // 成功获取 Banner 数据
                    val bannerData = UiData.BannerData(bannerResponse.data)
                    // 返回合并的数据
                    UiData.CombinedData(bannerData, articlePagingData)
                }.onFailure { error ->
                    // 处理 Banner 加载失败
                    UiState.Error(error.message ?: "Unknown error")
                }
            }.collect { combinedData ->
                _uiState.value = UiState.Success(combinedData)
            }
        }
    }



//    fun getCombinedData(page: Int) {
//        _uiState.value = UiState.Loading
//        viewModelScope.launch(exceptionHandler) {
//            val bannerDeferred = async { networkRepository.getBanner().first() }
//            val articleDeferred = async { networkRepository.createArticlePagingSource().first() }
//            val articles: Flow<PagingData<AriticleResponse.DataX>> = Pager(
//                config = PagingConfig(pageSize = 10),
//                pagingSourceFactory = { networkRepository.createArticlePagingSource()}
//            ).flow.cachedIn(viewModelScope)
//            try {
//                // 收集结果
//                val bannerResult = bannerDeferred.await()
//                val articleResult = articleDeferred.await()
//                bannerResult.onSuccess { bannerResponse ->
//                    articleResult.onSuccess { articleResponse ->
//                        // 合并数据
//                        _uiState.value = UiState.Success(UiData.CombinedData(bannerResponse.data, articleResponse))
//                    }
//                }
//            } catch (e: Exception) {
//                _uiState.value = UiState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }

}