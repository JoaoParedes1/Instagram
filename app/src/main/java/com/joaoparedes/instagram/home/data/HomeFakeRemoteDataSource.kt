package com.joaoparedes.instagram.home.data

import android.os.Handler
import android.os.Looper
import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Database
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.UserAuth

class HomeFakeRemoteDataSource: HomeDataSource {
    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val feed = Database.feeds[userUUID]

            callback.onSuccess(feed?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }

}