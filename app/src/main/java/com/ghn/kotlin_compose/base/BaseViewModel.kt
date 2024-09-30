package com.ghn.kotlin_compose.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @author 浩楠
 *
 * @date 2024/9/19-17:10.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
open class BaseViewModel : ViewModel() {
    // 处理网络请求中的异常
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        // 处理异常，如显示错误信息
    }

    // 使用 Flow 进行数据加载
    protected fun <T> flowDataLoad(
        block: suspend () -> T
    ): Flow<Result<T>> = flow {
        emit(Result.success(block()))
    }.catch { e ->
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)

    // 扩展函数来处理 Flow 数据
    protected fun <T> launchFlowDataLoad(
        flow: Flow<Result<T>>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch(exceptionHandler) {
            flow
                .collect { result ->
                    result.onSuccess { data -> onSuccess(data) }
                    result.onFailure { error -> onError(error) }
                }
        }
    }
}