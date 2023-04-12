package com.joaoparedes.instagram.home.data

import com.google.firebase.auth.FirebaseAuth
import com.joaoparedes.instagram.common.base.Cache
import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Database
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.UserAuth
import java.lang.RuntimeException

class HomeLocalDataSource(
    private val feedCache: Cache<List<Post>>,
): HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val posts = feedCache.get(userUUID)
        if (posts != null) {
            callback.onSuccess(posts)
        } else {
            callback.onFailure("posts não existem")
        }
        callback.onComplete()
    }

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuario não logado!!!")
    }

    override fun putFeed(response: List<Post>?) {
        feedCache.put(response)
    }
}