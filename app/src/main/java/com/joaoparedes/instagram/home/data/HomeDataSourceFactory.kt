package com.joaoparedes.instagram.home.data

import com.joaoparedes.instagram.common.base.Cache
import com.joaoparedes.instagram.common.model.Post

class HomeDataSourceFactory(
    private val feedCache: Cache<List<Post>>
) {

    fun createLocalDataSource(): HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }

    fun createRemoteDataSource(): FireHomeDataSource {
        return FireHomeDataSource()
    }

    fun createFromFeed(): HomeDataSource {
        if (feedCache.isCached()) {
            return HomeLocalDataSource(feedCache)
        }
        return FireHomeDataSource()
    }
}