package com.joaoparedes.instagram.home.data

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.UserAuth

class HomeRepository(private val dataSourceFactory: HomeDataSourceFactory) {

    fun fetchFeed(callback: RequestCallback<List<Post>>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userAuth = localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createFromFeed()

        dataSource.fetchFeed(userAuth.uuid, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                localDataSource.putFeed(data)
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }

        })
    }

    fun clearCache() {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        localDataSource.putFeed(null)
    }

}