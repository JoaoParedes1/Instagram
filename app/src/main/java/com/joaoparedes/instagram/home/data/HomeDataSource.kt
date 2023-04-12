package com.joaoparedes.instagram.home.data

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Post
import kotlin.UnsupportedOperationException

interface HomeDataSource {

    fun logout() { throw UnsupportedOperationException() }
    fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)
    fun fetchSession(): String { throw UnsupportedOperationException() }
    fun putFeed(response: List<Post>?) { throw UnsupportedOperationException() }
}