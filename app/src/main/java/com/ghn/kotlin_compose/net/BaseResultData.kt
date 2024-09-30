package com.ghn.kotlin_compose.net

/**
 * @author 浩楠
 *
 * @date 2024/9/19-17:29.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
data class BaseResultData<T>(
    val errorCode: Int,
    val data: T,
    val errorMsg: String
)

data class BannerBean(
    val title: String,
    val imagePath: String,
    val url: String
)

data class AriticleResponse(
        val curPage: Int,
        val datas: List<DataX>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
){
    data class DataX(
        val adminAdd: Boolean,
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
//        val desc: String,
//        val descMd: String,
//        val envelopePic: String,
//        val fresh: Boolean,
//        val host: String,
        val id: Int,
        val isAdminAdd: Boolean,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
//        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    ){
        fun getAuthorName() = author.ifBlank { "分享者: $shareUser" }
    }
//    {
//        data class Tag(
//            val name: String,
//            val url: String
//        )
//    }
}




