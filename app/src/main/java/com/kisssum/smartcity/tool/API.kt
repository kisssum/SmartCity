package com.kisssum.smartcity.tool

object API {
    // 学校  http://106.12.160.221:8080
    // 省赛  http://124.93.196.45:10002
    // 国赛  http://182.92.105.116:8080
    // v2.0  http://124.93.196.45:10001
    fun getBaseUrl(isProd: Boolean = false) = when (isProd) {
        true -> {
            "http://124.93.196.45:10001/prod-api/api"
        }
        else -> {
            "http://124.93.196.45:10001"
        }
    }

    // 引导页
    fun getGuideRotationListUrl() = "${getBaseUrl(true)}/rotation/list?pageNum=1&pageSize=8&type=1"
    fun getHomeRotationListUrl() = "${getBaseUrl(true)}/rotation/list?pageNum=1&pageSize=8&type=2"

    fun getNewsCategoryListUrl() = "${getBaseUrl()}/prod-api/press/category/list"
    fun getServiceListUrl() = "${getBaseUrl(true)}/service/list"

    fun getNewsListByTitleUrl(title: String) =
        "${getBaseUrl()}/prod-api/press/press/list?title=${title}&pageNum=0&pageSize=8&orderByColumn=publishDate desc"

    fun getNewsListUrl() =
        "${getBaseUrl()}/prod-api/press/press/list?pageNum=0&pageSize=8&orderByColumn=publishDate desc"

    fun getNewsTypeListUrl(type: Int) =
        "${getBaseUrl()}/prod-api/press/press/list?type=${type}&pageNum=0&pageSize=8&orderByColumn=publishDate desc"

    fun getNewsDetailUrl(id: Int) = "${getBaseUrl()}/prod-api/press/press/${id}"
    fun getNewsCommentsListUrl(id: Int) =
        "${getBaseUrl()}/prod-api/press/comments/list?newsId=${id}"

    fun getNewsCommentAddUrl() =
        "${getBaseUrl()}/prod-api/press/pressComment"

    fun getNewsCommentLikeUrl(id: Int) =
        "${getBaseUrl()}/prod-api/press/pressComment/like/${id}"

    fun getUserLoginUrl() = "${getBaseUrl(true)}/login"
    fun getUserRegisterUrl() = "${getBaseUrl(true)}/register"
    fun getUserInfoUrl() = "${getBaseUrl(true)}/common/user/getInfo"
    fun getUserUpdata() = "${getBaseUrl(true)}/common/user"
    fun getFeedBack() = "${getBaseUrl(true)}/common/feedback"
}